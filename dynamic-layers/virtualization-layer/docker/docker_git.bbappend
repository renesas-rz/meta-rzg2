FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "\
    file://update_date_time.sh \
    file://update_date_time.service \
    file://normalize-aarch64-architectures-to-arm64.patch;patchdir=${S}/src/import/vendor/github.com/docker/swarmkit \
"

RDEPENDS_${PN} += "bash"

SYSTEMD_SERVICE_${PN} += "${@bb.utils.contains('DISTRO_FEATURES','systemd','update_date_time.service','',d)}"

INITSCRIPT_NAME_${PN} += "${@bb.utils.contains('DISTRO_FEATURES','sysvinit','update_date_time.sh','',d)}"

do_install_append() {

    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
        install -m 0755 ${WORKDIR}/update_date_time.sh ${D}${bindir}/update_date_time.sh
        install -m 644 ${WORKDIR}/update_date_time.service ${D}/${systemd_unitdir}/system
    else
        install -m 0755 ${WORKDIR}/update_date_time.sh ${D}${sysconfdir}/init.d/update_date_time.sh
    fi
}
