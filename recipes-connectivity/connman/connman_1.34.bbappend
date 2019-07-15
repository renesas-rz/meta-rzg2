require include/docker-control.inc

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append = "\
  ${@base_conditional("USE_DOCKER", "1", " file://0001-connmand-systemd-Do-not-act-as-a-DNS-proxy.patch ", "", d)} \
"

do_install_append() {
    if ${@base_conditional('USE_DOCKER', '1', 'true', 'false', d)} &&
       ${@bb.utils.contains('DISTRO_FEATURES','sysvinit','true','false',d)} &&
       [ -e ${D}${sysconfdir}/init.d/connman ]; then

        sed -i 's/\$DAEMON \$EXTRA_PARAM$/\$DAEMON \$EXTRA_PARAM --nodnsproxy/' ${D}${sysconfdir}/init.d/connman
    fi
}
