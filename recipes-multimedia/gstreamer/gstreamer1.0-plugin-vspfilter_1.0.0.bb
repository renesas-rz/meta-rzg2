SUMMARY = "GStreamer VSP filter plugin"
SECTION = "multimedia"
LICENSE = "GPLv2+"
DEPENDS = "gstreamer1.0 gstreamer1.0-plugins-base pkgconfig"
LIC_FILES_CHKSUM = "file://COPYING.LIB;md5=4fbd65380cdd255951079008b364516c"
inherit autotools pkgconfig

PN = "gstreamer1.0-plugin-vspfilter"

EXTRA_AUTORECONF_append = " -I ${STAGING_DATADIR}/aclocal"

VSPFILTER_CONF_r8a774a1 = "gstvspfilter-${MACHINE}_r8a774a1.conf"
VSPFILTER_CONF_r8a774b1 = "gstvspfilter-${MACHINE}_r8a774b1.conf"
VSPFILTER_CONF_r8a774c0 = "gstvspfilter-${MACHINE}_r8a774c0.conf"
VSPFILTER_CONF_r8a774e1 = "gstvspfilter-${MACHINE}_r8a774e1.conf"

SRC_URI = " \
    git://github.com/renesas-rcar/gst-plugin-vspfilter.git;branch=RCAR-GEN3/1.0.0 \
    file://${VSPFILTER_CONF} \
    file://0001-vspfilter-Increase-number-of-buffers.patch \
"

SRCREV = "c506c57b6c169e0cd9578a294a829640a531c2d4"

S = "${WORKDIR}/git"

FILES_${PN} = " \
    ${libdir}/gstreamer-1.0/libgstvspfilter.so \
    ${sysconfdir}/gstvspfilter.conf"
FILES_${PN}-dev = "${libdir}/gstreamer-1.0/libgstvspfilter.la"
FILES_${PN}-staticdev = "${libdir}/gstreamer-1.0/libgstvspfilter.a"
FILES_${PN}-dbg = " \
    ${libdir}/gstreamer-1.0/.debug \
    ${prefix}/src"

do_install_append() {
    install -Dm 644 ${WORKDIR}/${VSPFILTER_CONF} ${D}/etc/gstvspfilter.conf
}

RDEPENDS_${PN} = "kernel-module-vsp2driver"
