FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "\
    file://set_dns_nameserver.sh \
    file://update_date_time.sh \
    file://set_dns_nameserver.service \
    file://update_date_time.service \
"

RDEPENDS_${PN} += "bash"

SYSTEMD_SERVICE_${PN} += "${@bb.utils.contains('DISTRO_FEATURES','systemd','set_dns_nameserver.service update_date_time.service','',d)}"

INITSCRIPT_NAME_${PN} += "${@bb.utils.contains('DISTRO_FEATURES','sysvinit','set_dns_nameserver.sh update_date_time.sh','',d)}"

do_install_append() {

    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
        install -m 0755 ${WORKDIR}/set_dns_nameserver.sh ${D}${bindir}/set_dns_nameserver.sh
        install -m 0755 ${WORKDIR}/update_date_time.sh ${D}${bindir}/update_date_time.sh

        install -m 644 ${WORKDIR}/set_dns_nameserver.service ${D}/${systemd_unitdir}/system
        install -m 644 ${WORKDIR}/update_date_time.service ${D}/${systemd_unitdir}/system
    else
        install -m 0755 ${WORKDIR}/set_dns_nameserver.sh ${D}${sysconfdir}/init.d/set_dns_nameserver.sh
        install -m 0755 ${WORKDIR}/update_date_time.sh ${D}${sysconfdir}/init.d/update_date_time.sh
    fi
}
