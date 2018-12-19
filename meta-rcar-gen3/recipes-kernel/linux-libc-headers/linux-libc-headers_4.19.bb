require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

RENESAS_BSP_URL = " \
    git://github.com/renesas-rz/renesas-cip.git"
BRANCH = "rcar-gen3/v4.19"
SRCREV = "0e68ba09ee8f047642aec15dc725d24e4e3a2b77"
LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

SRC_URI = "${RENESAS_BSP_URL};branch=${BRANCH}"

SRC_URI_append = " \
    file://0001-linux-add-time.h-header-file-to-net_tstamp.h.patch \
"

S = "${WORKDIR}/git"

DEPENDS = "bison-native flex-native"
