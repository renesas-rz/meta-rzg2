FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}/:"
SRC_URI_remove = "http://gstreamer.freedesktop.org/src/gst-plugins-bad/gst-plugins-bad-${PV}.tar.xz"
SRC_URI_append = " \
    git://github.com/renesas-rcar/gst-plugins-bad.git;branch=RCAR-GEN3/1.12.2;name=base \
    git://anongit.freedesktop.org/gstreamer/common;destsuffix=git/common;name=common \
"

SRCREV_base = "db554fad172f2dabb0f7a75ef1e8e4cb35e172c9"
SRCREV_common = "48a5d85ebf4a0bad1c997c83100f710fe2154fbf"
SRCREV_FORMAT = "base"

DEPENDS += "weston bayer2raw gles-user-module mmngr-user-module mmngrbuf-user-module"

S = "${WORKDIR}/git"

do_configure_prepend() {
    cd ${S}
    ./autogen.sh --noconfigure
    cd ${B}
}

SRC_URI_append += " \
    file://0001-waylandsink-Add-set-window-position.patch \
    file://0002-waylandsink-Add-set-window-scale-feature.patch \
    file://0003-waylandsink-Add-fullscreen-display-feature.patch \
    file://0004-gstreamer-waylandsink-disable-subsurface-in-fullscre.patch \
    file://0005-New-libbayersink-Bayer-to-RAW-converter-and-display-.patch \
    file://0006-ext-bayerconvert-add-bayerconvert-plugin.patch \
"

RDEPENDS_gstreamer1.0-plugins-bad += "libwayland-egl"
RDEPENDS_gstreamer1.0-plugins-bad-bayersink += "bayer2raw"
RDEPENDS_gstreamer1.0-plugins-bad-bayerconvert += "bayer2raw"

PACKAGECONFIG_append = " faac faad"
