DESCRIPTION = "Bayer2Raw conversion Library"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://GPL-COPYING;md5=9450224a11928f85794c948d3539a882"
 
PR = "r0"
 
SRC_URI = " \
	file://bayer2raw.tar.gz \
        file://0001-Remove-empty-output-surface-and-use-glTexSubImage2D-.patch \
        file://0002-Use-EGLImageKHR-to-store-the-output-data-in-user-mem.patch \
        file://0003-bayer2raw-Correct-condition-for-first-draw-data-to-t.patch \
"

inherit pkgconfig

DEPENDS = "virtual/libgles2"

S = "${WORKDIR}/bayer2raw"

PACKAGES = "${PN}-dbg ${PN}"
FILES_${PN} = " \
	${libdir}/* \
	${prefix}/* \
"

FILES_${PN}-dbg = " \
	${libdir}/.debug \
"

do_install() {
	install -d ${D}${libdir}
	install -m 755 ${S}/*.so ${D}${libdir}

	install -d ${D}${prefix}/share
	install -m 755 ${S}/bayer.frag ${D}${prefix}/share
	install -m 755 ${S}/bayer.vert ${D}${prefix}/share

	install -d ${D}${includedir}
	install -m 644 ${S}/bayer2raw.h ${D}/${includedir}/
}

INSANE_SKIP_${PN} = "ldflags"
