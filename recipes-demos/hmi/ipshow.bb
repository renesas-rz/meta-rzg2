LICENSE = "CLOSED"
DESCRIPTION = "IPShow"

DEPENDS += " libegl gles-user-module weston wayland cairo"

SRC_URI = " \
 file://IPShow.tar \
"

S = "${WORKDIR}/IPShow"

SRC = "${THISDIR}/IPShow"

inherit pkgconfig

INSANE_SKIP_${PN} = "already-stripped ldflags"
RDEPENDS_libweston-2_append = " gles-user-module "

do_compile() {
  cd ${WORKDIR}/IPShow
  make
}

do_install() {
  install -d ${D}/home/root/IPShow
  install ${WORKDIR}/IPShow/IPShow ${D}/home/root/IPShow
}

RDEPENDS_${PN} += "gles-user-module"
FILES_${PN}-dev = "${libdir}/* ${includedir}"
FILES_${PN} = "/home/root/IPShow/* \
"

FILES_${PN}-dbg += " \
 /home/root/IPShow/.debug/* \
"
