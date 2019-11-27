#ifndef _BAYER2RAW_H
#define _BAYER2RAW_H

#include <EGL/egl.h>
#include <EGL/eglext.h>
#include <EGL/eglplatform.h>
#include <GLES2/gl2.h>

struct context_bayer2rgb
{
  /* Public */
  int width;
  int height;
  EGLDisplay  display;
  EGLContext  context;
  EGLSurface  surface;

  /* Intern */
  /* Colorspace conversion specific attributes */
  GLuint programHandle;
  GLuint bayer_texture;
  GLuint rgb_texture;
};

/* Required: all public members */
int init_bayer_rgb_conversion(struct context_bayer2rgb *ctx);

/* Get the bayer to RGB conversion done */
void do_bayer_rgb_conversion(struct context_bayer2rgb *ctx, void *in_buf,
    void *out_buf);
void do_bayer_rgb_conversion_texture(struct context_bayer2rgb *ctx, void *in_buf,
    GLuint *texture);

#endif // _BAYER2RAW_H
