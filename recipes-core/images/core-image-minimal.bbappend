require include/core-image-renesas-base.inc

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

IMAGE_INSTALL_append = " util-linux \
"

do_install:append() {
    sed -i 's/\\u@\\h:\\w\\$ /\\[\\033[01;32m\\]\\u@\\h\\[\\033[00m\\]:\\[\\033[01;34m\\]\\w\\[\\033[00m\\]\\$ /' ${D}${sysconfdir}/profile
}


