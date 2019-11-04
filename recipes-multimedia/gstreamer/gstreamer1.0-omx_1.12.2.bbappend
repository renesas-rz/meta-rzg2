FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}/:"

SRC_URI_remove = "http://gstreamer.freedesktop.org/src/gst-omx/gst-omx-${PV}.tar.xz"
SRC_URI_append = " \
    git://github.com/renesas-rcar/gst-omx.git;branch=RCAR-GEN3/1.12.2;name=base \
    git://anongit.freedesktop.org/gstreamer/common;destsuffix=git/common;name=common \
    file://gstomx.conf \
    file://0001-omxvideodec-don-t-drop-frame-if-it-contains-header-d.patch \
"

require include/rzg2-path-common.inc

DEPENDS += "omx-user-module mmngrbuf-user-module"

SRCREV_base = "ed1ee64763113abb3286e203f08f84922a42e9c6"
SRCREV_common = "48a5d85ebf4a0bad1c997c83100f710fe2154fbf"
SRCREV_FORMAT = "base"

LIC_FILES_CHKSUM = "file://COPYING;md5=4fbd65380cdd255951079008b364516c \
    file://omx/gstomx.h;beginline=1;endline=22;md5=41f577b291a84518889deaaaf2bcbc95 \
"

S = "${WORKDIR}/git"

GSTREAMER_1_0_OMX_TARGET = "rcar"
GSTREAMER_1_0_OMX_CORE_NAME = "${libdir}/libomxr_core.so"
EXTRA_OECONF_append = " --enable-experimental"

do_configure_prepend() {
    cd ${S}
    install -m 0644 ${WORKDIR}/gstomx.conf ${S}/config/rcar/
    sed -i 's,@RENESAS_DATADIR@,${RENESAS_DATADIR},g' ${S}/config/rcar/gstomx.conf
    ./autogen.sh --noconfigure
    cd ${B}
}

RDEPENDS_${PN}_append = " libwayland-egl omx-user-module"
RDEPENDS_${PN}_remove = "libomxil"
