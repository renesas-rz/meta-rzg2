inherit systemd

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_SERVICE_${PN} = "vin.service"

SRC_URI = " \
	file://vin-init.sh \
	file://vin.service \
	file://COPYING.MIT \
"
S = "${WORKDIR}"

FILES_${PN} += " \
	${systemd_unitdir}/system/vin.service \
	/home/root/vin-init.sh \
"

do_install() {
	install -d ${D}/home/root
	install -m 0744 ${WORKDIR}/vin-init.sh ${D}/home/root
	install -d ${D}/${systemd_unitdir}/system
	install -m 0644 ${WORKDIR}/vin.service ${D}/${systemd_unitdir}/system
}
