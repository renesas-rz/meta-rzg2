SUMMARY = "Startup script for WL18xx modules"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI = "file://wl18xx-init \
  file://COPYING.MIT \
"

S = "${WORKDIR}"

do_install() {
	install -d ${D}/${sysconfdir}/init.d
	install -m755 ${WORKDIR}/wl18xx-init ${D}/${sysconfdir}/init.d/rc.wl18xx
}

inherit allarch update-rc.d

INITSCRIPT_NAME = "rc.wl18xx"
INITSCRIPT_PARAMS = "start 8 5 2 . stop 21 0 1 6 ."
