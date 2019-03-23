require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

RENESAS_BSP_URL = " \
    git://github.com/renesas-rz/renesas-cip.git"
BRANCH = "v4.19.13-cip1"
SRCREV = "8392851962b980fc406de9be9bd7ef14fd6f68b0"
LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

SRC_URI = "${RENESAS_BSP_URL};branch=${BRANCH}"

SRC_URI_append = " \
    file://0001-linux-add-time.h-header-file-to-net_tstamp.h.patch \
"

S = "${WORKDIR}/git"

DEPENDS = "bison-native flex-native"
