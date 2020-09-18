require weston.inc

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

BRANCH = "rcar-gen3/2.0.0/gl-fallback"

SRCREV = "84709ddcbf1e94edae96038f530e9ddd855f707f"

SRC_URI_remove = "https://wayland.freedesktop.org/releases/${BPN}-${PV}.tar.xz"

SRC_URI_append = " \
    git://github.com/renesas-rcar/weston.git;branch=${BRANCH} \
    file://weston.png \
    file://weston.desktop \
    file://xwayland.weston-start \
    file://weston.ini \
    file://weston_v4l2.ini \
    file://weston_ek874.ini \
    file://weston.sh \
    file://1001-Share-toytoolkit-lib.patch \
    file://1008-larger-weston-bar-suitable-for-touch-screen.patch \
    file://0001-desktop-shell-Position-maximized-surfaces-on-the-cor.patch \
    file://0001-weston-add-weston_desktop_api_set_xwayland_position.patch \
    file://0001-compositor-main-Change-default-name-of-weston-device.patch \
    file://add-symlink-vsp.rules \
"

#Fix build break with glibc 2.28
SRC_URI += "${@'file://Fix-build-error-major-minor.patch' if 'Buster' in '${CIP_MODE}' else ' '}"

S = "${WORKDIR}/git"

PACKAGECONFIG_append = " \
    ${@base_conditional('USE_MULTIMEDIA', '1', ' v4l2', '', d)} \
"
PACKAGECONFIG[v4l2] = " --enable-v4l2, --disable-v4l2,,kernel-module-vsp2driver"

do_install_append() {
    install -d ${D}/${sysconfdir}/xdg/weston
    if [ "X${USE_MULTIMEDIA}" = "X1" ]; then
        # install weston.ini as sample settings of v4l2-renderer
        if [ "${MACHINE}" = "ek874" ] ; then
            install -m 644 ${WORKDIR}/weston_ek874.ini ${D}/${sysconfdir}/xdg/weston/weston.ini
        fi

        if [ "${MACHINE}" = "hihope-rzg2m" ] ; then
            install -m 644 ${WORKDIR}/weston_v4l2.ini ${D}/${sysconfdir}/xdg/weston/weston.ini
        fi

        if [ "${MACHINE}" = "hihope-rzg2n" ] ; then
            install -m 644 ${WORKDIR}/weston_v4l2.ini ${D}/${sysconfdir}/xdg/weston/weston.ini
        fi

        if [ "${MACHINE}" = "hihope-rzg2h" ] ; then
            install -m 644 ${WORKDIR}/weston_v4l2.ini ${D}/${sysconfdir}/xdg/weston/weston.ini
        fi
    else
        # install weston.ini as sample settings of gl-renderer
        install -m 644 ${WORKDIR}/weston.ini ${D}/${sysconfdir}/xdg/weston/
    fi

    # Checking for ivi-shell configuration
    # If ivi-shell is enable, we will add its configs to weston.ini
    if [ "X${USE_WAYLAND_IVI_SHELL}" = "X1" ]; then
        sed -i '/repaint-window=34/c\repaint-window=34\nshell=ivi-shell.so' \
            ${D}/${sysconfdir}/xdg/weston/weston.ini
        sed -e '$a\\' \
            -e '$a\[ivi-shell]' \
            -e '$a\ivi-module=ivi-controller.so' \
            -e '$a\ivi-input-module=ivi-input-controller.so' \
            -e '$a\transition-duration=300' \
            -e '$a\cursor-theme=default' \
            -i ${D}/${sysconfdir}/xdg/weston/weston.ini
    fi

    # Set XDG_RUNTIME_DIR to /run/user/$UID (e.g. run/user/0)
    install -d ${D}/${sysconfdir}/profile.d
    install -m 0755 ${WORKDIR}/weston.sh ${D}/${sysconfdir}/profile.d/weston.sh

    install -d ${D}/${sysconfdir}/udev/rules.d/
    install ${WORKDIR}/add-symlink-vsp.rules ${D}/${sysconfdir}/udev/rules.d/
}

FILES_${PN}_append = " \
    ${sysconfdir}/xdg/weston/weston.ini \
    ${sysconfdir}/profile.d/weston.sh \
    ${sysconfdir}/udev/rules.d/add-symlink-vsp.rules \
"
