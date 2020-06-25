SUMMARY = "OpenGL apps"
DESCRIPTION = "Compile and isntall 3D GFX apps of PowerVR \
with Wayland environment using Renesas GFX"
SECTION = "GFX"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

DEPENDS = "gles-user-module weston"

#Repository TAG: R15.1-v3.5
SRCREV = "d042dd42d9cd205b621402a99a636da6d00b8c79"
SRC_URI = " \
    git://github.com/powervr-graphics/Native_SDK;branch=master;protocol=git \
    file://build_64_wl.patch \
    file://0003-Wayland-patch-setting-position.patch \
    file://0002-prevent-error-convert-bool-to-void.patch \
"

S = "${WORKDIR}/git"

OGLES2_COMPILE_PATH = "OGLES2/Build/LinuxGeneric/"
OGLES2_INSTALL_PATH = "OGLES2/Build/Linux_aarch64/ReleaseWayland/"
OGLES3_COMPILE_PATH = "OGLES3/Build/LinuxGeneric/"
OGLES3_INSTALL_PATH = "OGLES3/Build/Linux_aarch64/ReleaseWayland/"

INSANE_SKIP_${PN}-dev = "ldflags"
TARGET_CC_ARCH += "${LDFLAGS}"
INSANE_SKIP_${PN} = "ldflags"


do_configure_append() {
    # Remove the current headers of EGL, use ones from Renesas GFX
    rm ${S}/Builds/Include/EGL/*
    install -m 0644 ${STAGING_DIR_HOST}/usr/include/EGL/* \
        ${S}/Builds/Include/EGL/
}

do_compile() {
    # Export neccessary variables for Makefile
    export CROSS_COMPILE="${TARGET_PREFIX}"
    export LIBDIR="${PKG_CONFIG_SYSROOT_DIR}/usr/lib"
    export PLATFORM="Linux_aarch64"
    unset DISCIMAGE
    unset TOOLCHAIN
    export WAYLANDBUILD=1
    export PLAT_CFLAGS="--sysroot=${PKG_CONFIG_SYSROOT_DIR}"

    # Complile for OpenGLES3
    make -C ${S}/Examples/Intermediate/ColourGrading/${OGLES3_COMPILE_PATH}
    make -C ${S}/Examples/Advanced/Coverflow/${OGLES3_COMPILE_PATH}
    make -C ${S}/Examples/Advanced/FilmTV/${OGLES3_COMPILE_PATH}
    make -C ${S}/Examples/Advanced/Navigation3D/${OGLES3_COMPILE_PATH}
    make -C ${S}/Examples/Advanced/Water/${OGLES3_COMPILE_PATH}
    make -C ${S}/Examples/Advanced/DeferredShading/${OGLES3_COMPILE_PATH}

    # Complile for OpenGLES2
    make -C ${S}/Examples/Advanced/DeferredShading/${OGLES2_COMPILE_PATH}
    make -C ${S}/Examples/Advanced/Navigation3D/${OGLES2_COMPILE_PATH}
    make -C ${S}/Examples/Advanced/Coverflow/${OGLES2_COMPILE_PATH}
    make -C ${S}/Examples/Advanced/FilmTV/${OGLES2_COMPILE_PATH}
    make -C ${S}/Examples/Advanced/Fractal/${OGLES2_COMPILE_PATH}
}

do_install() {
    # Install compiled binaries to rootfs
    install -d ${D}/usr/bin

    # OpenGLES3 app installing
    install -m 755 ${S}/Examples/Intermediate/ColourGrading/${OGLES3_INSTALL_PATH}/OGLES3ColourGrading ${D}/usr/bin/
    install -m 755 ${S}/Examples/Advanced/Coverflow/${OGLES3_INSTALL_PATH}/OGLES3Coverflow ${D}/usr/bin/
    install -m 755 ${S}/Examples/Advanced/FilmTV/${OGLES3_INSTALL_PATH}/OGLES3FilmTV ${D}/usr/bin/
    install -m 755 ${S}/Examples/Advanced/Navigation3D/${OGLES3_INSTALL_PATH}/OGLES3Navigation3D ${D}/usr/bin/
    install -m 755 ${S}/Examples/Advanced/Water/${OGLES3_INSTALL_PATH}/OGLES3Water ${D}/usr/bin/
    install -m 755 ${S}/Examples/Advanced/DeferredShading/${OGLES3_INSTALL_PATH}/OGLES3DeferredShading ${D}/usr/bin/

    # OpenGLES2 app installing
    install -m 755 ${S}/Examples/Advanced/DeferredShading/${OGLES2_INSTALL_PATH}/OGLES2DeferredShading ${D}/usr/bin/
    install -m 755 ${S}/Examples/Advanced/Navigation3D/${OGLES2_INSTALL_PATH}/OGLES2Navigation3D ${D}/usr/bin/
    install -m 755 ${S}/Examples/Advanced/Coverflow/${OGLES2_INSTALL_PATH}/OGLES2Coverflow ${D}/usr/bin/
    install -m 755 ${S}/Examples/Advanced/FilmTV/${OGLES2_INSTALL_PATH}/OGLES2FilmTV ${D}/usr/bin/
    install -m 755 ${S}/Examples/Advanced/Fractal/${OGLES2_INSTALL_PATH}/OGLES2Fractal ${D}/usr/bin/
}

# Add runtime dependency for openGL
RDEPENDS_${PN} = "libudev wayland libwayland-egl"
