SUMMARY = "Startup script for WL18xx modules"
LICENSE = "CLOSED"

SRC_URI = "file://wl18xx-init"

S = "${WORKDIR}"

do_install() {
	install -d ${D}/${sysconfdir}/init.d
	install -m755 ${WORKDIR}/wl18xx-init ${D}/${sysconfdir}/init.d/rc.wl18xx
}

inherit allarch update-rc.d

INITSCRIPT_NAME = "rc.wl18xx"
INITSCRIPT_PARAMS = "start 8 5 2 . stop 21 0 1 6 ."
