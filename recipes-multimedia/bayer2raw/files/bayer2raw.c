#include <fcntl.h>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "bayer2raw.h"

#define EGLGLES_BAYER2RAW_SHADER_PATH "/usr/share/"

#define PRINT_OGL_ERROR() print_opengl_error(__FILE__, __LINE__)

/* opengl screen_vertices for texture mapping */
static const GLfloat screen_vertices[][2] = {
  {-1.0f, -1.0f},
  {1.0f, -1.0f},
  {-1.0f, 1.0f},
  {1.0f, 1.0f}
};

static const GLfloat texture_coords[][2] = {
  {0.0f, 1.0f},
  {1.0f, 1.0f},
  {0.0f, 0.0f},
  {1.0f, 0.0f}
};

static int
compile_shader (const char *FName, GLuint shader_handle)
{
  FILE *fptr = NULL;
  fptr = fopen (FName, "rb");

  if (fptr == NULL)
    return -1;

  int length;
  fseek (fptr, 0, SEEK_END);
  length = ftell (fptr);
  fseek (fptr, 0, SEEK_SET);

  char *shaderSource = malloc (sizeof (char) * (unsigned int) length);
  if (shaderSource == NULL)
    return -1;
  if (!fread (shaderSource, (size_t) length, 1, fptr))
    return -1;

  glShaderSource (shader_handle, 1, (const char *const *) &shaderSource,
      &length);
  glCompileShader (shader_handle);

  free (shaderSource);
  fclose (fptr);

  GLint compiled = 0;
  glGetShaderiv (shader_handle, GL_COMPILE_STATUS, &compiled);
  if (!compiled) {
    /* Retrieve error buffer size. */
    GLint error_buf_size;
    GLsizei error_length;
    glGetShaderiv (shader_handle, GL_INFO_LOG_LENGTH, &error_buf_size);

    char *info_log = malloc ((unsigned int) error_buf_size * sizeof (char) + 1);
    if (info_log) {
      /* Retrieve error. */
      glGetShaderInfoLog (shader_handle, error_buf_size,
          &error_length, info_log);
      info_log[error_buf_size + 1] = '\0';
      printf ("%s\n", info_log);
      free (info_log);
    }

    printf ("Error compiling shader '%s'\n", FName);
    return -1;
  }

  return 0;
}

static int
load_shaders (const char *file_vertex, const char *file_frag,
    GLuint * program_handle)
{
  GLuint pixel_shader_handle;
  GLuint vert_shader_handle;

  vert_shader_handle = glCreateShader (GL_VERTEX_SHADER);
  pixel_shader_handle = glCreateShader (GL_FRAGMENT_SHADER);

  if (compile_shader (file_vertex, vert_shader_handle) == -1)
    return -1;

  if (compile_shader (file_frag, pixel_shader_handle) == -1)
    return -1;

  *program_handle = glCreateProgram ();
  glAttachShader (*program_handle, vert_shader_handle);
  glAttachShader (*program_handle, pixel_shader_handle);
  glLinkProgram (*program_handle);

  /* Check if linking succeeded. */
  GLint linked = GL_FALSE;
  glGetProgramiv (*program_handle, GL_LINK_STATUS, &linked);
  if (!linked) {
    /* Retrieve error buffer size. */
    GLint errorBufSize, errorLength;
    glGetShaderiv (*program_handle, GL_INFO_LOG_LENGTH, &errorBufSize);

    char *infoLog = malloc ((unsigned int) errorBufSize * sizeof (char) + 1);
    if (infoLog) {
      /* Retrieve error. */
      glGetProgramInfoLog (*program_handle, errorBufSize,
          &errorLength, infoLog);
      infoLog[errorBufSize + 1] = '\0';

      free (infoLog);
    }
    return -1;
  }

  return 0;
}

void
print_opengl_error (const char *file, int line)
{
  GLenum err;

  err = glGetError ();

  while (err != GL_NO_ERROR) {
    char error[128];

    switch (err) {
      case GL_INVALID_OPERATION:
        strcpy (error, "INVALID_OPERATION");
        break;
      case GL_INVALID_ENUM:
        strcpy (error, "INVALID_ENUM");
        break;
      case GL_INVALID_VALUE:
        strcpy (error, "INVALID_VALUE");
        break;
      case GL_OUT_OF_MEMORY:
        strcpy (error, "OUT_OF_MEMORY");
        break;
      case GL_INVALID_FRAMEBUFFER_OPERATION:
        strcpy (error, "INVALID_FRAMEBUFFER_OPERATION");
        break;
    }

    printf ("GL_%s - %s:%d\n", error, file, line);
    err = glGetError ();
  }
}

int
init_bayer_rgb_conversion (struct context_bayer2rgb *ctx)
{
  int ret;
  GLint loc_vertices;
  GLint loc_texcoord;

  if (!eglMakeCurrent (ctx->display, ctx->surface, ctx->surface,
      ctx->context)) {
    printf ("[ERROR] : Could not make the current window current !\n");
    return -1;
  }

  /* Prepare pipeline */
  ret = load_shaders (EGLGLES_BAYER2RAW_SHADER_PATH "bayer.vert",
      EGLGLES_BAYER2RAW_SHADER_PATH "bayer.frag", &ctx->programHandle);
  if (ret == -1) {
    printf ("Can not load shaders\n");
    return -1;
  }
  glUseProgram (ctx->programHandle);
  PRINT_OGL_ERROR ();

  /* Grab location of shader attributes. */
  loc_vertices = glGetAttribLocation (ctx->programHandle, "position");
  loc_texcoord = glGetAttribLocation (ctx->programHandle, "uv");
  PRINT_OGL_ERROR ();

  /* Enable vertex arrays to push the data. */
  glEnableVertexAttribArray ((GLuint) loc_vertices);
  glEnableVertexAttribArray ((GLuint) loc_texcoord);
  PRINT_OGL_ERROR ();

  /* Set data in the arrays. */
  glVertexAttribPointer ((GLuint) loc_vertices, 2, GL_FLOAT, GL_FALSE, 0,
      &screen_vertices[0][0]);
  glVertexAttribPointer ((GLuint) loc_texcoord, 2, GL_FLOAT, GL_FALSE, 0,
      &texture_coords[0][0]);
  PRINT_OGL_ERROR ();

  /* Set the size inside fragment shader */
  GLint loc_source_size =
      glGetUniformLocation (ctx->programHandle, "sourceSize");
  glUniform4f (loc_source_size, (float) ctx->width, (float) ctx->height,
      (1.0f / (float)ctx->width), 1.0f / ((float) ctx->height));
  PRINT_OGL_ERROR ();

  /* Define the location of the first red pixel in the bayer matrix */
  GLint loc_first_red = glGetUniformLocation (ctx->programHandle, "firstRed");
  glUniform2f (loc_first_red, 1.0f, 1.0f);
  PRINT_OGL_ERROR ();

  /* Generate the output texture */
  glGenTextures (1, &ctx->rgb_texture);
  PRINT_OGL_ERROR ();

  /* Generate the input texture */
  glGenTextures (1, &ctx->bayer_texture);
  glBindTexture (GL_TEXTURE_2D, ctx->bayer_texture);
  glTexParameteri (GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
  glTexParameteri (GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
  glTexParameteri (GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
  glTexParameteri (GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
  PRINT_OGL_ERROR ();

  return 0;
}

void
do_bayer_rgb_conversion (struct context_bayer2rgb *ctx, void *in_buf,
    void *out_buf)
{
  /* Empty the RGB texture */
  glBindTexture (GL_TEXTURE_2D, ctx->rgb_texture);
  glTexImage2D (GL_TEXTURE_2D, 0, GL_RGB, ctx->width, ctx->height,
      0, GL_RGB, GL_UNSIGNED_BYTE, 0);

  /* Map the bayer buffer */
  glBindTexture (GL_TEXTURE_2D, ctx->bayer_texture);
  glTexImage2D (GL_TEXTURE_2D, 0, GL_LUMINANCE, ctx->width, ctx->height,
      0, GL_LUMINANCE, GL_UNSIGNED_BYTE, in_buf);

  /* Draw on framebufer */
  glDrawArrays (GL_TRIANGLE_STRIP, 0, 4);

  /* Get back the buffer */
  glReadPixels (0, 0, ctx->width, ctx->height, GL_RGBA,
      GL_UNSIGNED_BYTE, out_buf);
  PRINT_OGL_ERROR ();
}

void
do_bayer_rgb_conversion_texture(struct context_bayer2rgb *ctx, void *in_buf,
    GLuint *texture)
{
  /* Empty the RGB texture */
  glBindTexture (GL_TEXTURE_2D, ctx->rgb_texture);
  glTexImage2D (GL_TEXTURE_2D, 0, GL_RGB, ctx->width, ctx->height,
      0, GL_RGB, GL_UNSIGNED_BYTE, 0);

  /* Map the bayer buffer */
  glBindTexture (GL_TEXTURE_2D, ctx->bayer_texture);
  glTexImage2D (GL_TEXTURE_2D, 0, GL_LUMINANCE, ctx->width, ctx->height,
      0, GL_LUMINANCE, GL_UNSIGNED_BYTE, in_buf);

  /* Draw on framebufer */
  glDrawArrays (GL_TRIANGLE_STRIP, 0, 4);

  *texture = ctx->rgb_texture;
}
