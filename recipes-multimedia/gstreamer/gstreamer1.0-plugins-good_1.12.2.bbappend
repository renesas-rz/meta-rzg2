FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}/:"
SRC_URI_remove = "http://gstreamer.freedesktop.org/src/gst-plugins-good/gst-plugins-good-${PV}.tar.xz"
SRC_URI_append = " \
    git://github.com/renesas-rcar/gst-plugins-good.git;branch=RCAR-GEN3/1.12.2;name=base \
    git://anongit.freedesktop.org/gstreamer/common;destsuffix=git/common;name=common \
"

SRCREV_base = "3fd901306fb0fad520e7cbb6a3b0acc79e810700"
SRCREV_common = "48a5d85ebf4a0bad1c997c83100f710fe2154fbf"
SRCREV_FORMAT = "base"

DEPENDS += "mmngrbuf-user-module"

S = "${WORKDIR}/git"

SRC_URI_append = " \
  file://0001-v4l2object-Validate-colorimetry-in-S-TRY_FMT.patch \
  file://0002-v4l2object-Introduce-quirk-to-skip-slow-probes.patch \
  file://0003-v4l2src-Speedup-camera-startup-by-skipping-try_fmt.patch \
  file://0004-v4l2src-Ensure-all-caps-a-fixated.patch \
"

EXTRA_OECONF_append = " \
    --enable-cont-frame-capture \
    --enable-ignore-fps-of-video-standard \
"

do_configure_prepend() {
    cd ${S}
    ./autogen.sh --noconfigure
    cd ${B}
}
