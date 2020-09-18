#Fix build break with glibc 2.28
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
SRC_URI += "${@'file://0001-pppd-Use-openssl-for-the-DES-instead-of-the-libcrypt.patch \
                file://0002-pppd-Fix-compile-error-due-to-wrong-include-location.patch \
' if 'Buster' in '${CIP_MODE}' else ' '}"

#Need openssl for des.h
DEPENDS += "openssl"
