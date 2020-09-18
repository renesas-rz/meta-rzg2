# Glamor for Xorg requires gbm >= 10.2.0.
# Glamor is not necessary with current env, so disable it.
PACKAGECONFIG_remove = "glamor"

#Fix build break with glibc 2.28
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
SRC_URI += "${@'file://0001-xfree86-Silence-a-new-glibc-warning.patch' if 'Buster' in '${CIP_MODE}' else ' '}"
