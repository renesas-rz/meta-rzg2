LIC_FILES_CHKSUM = "file://LICENSE.md;md5=7f62d8fc087d1e90350a140c9f8c8e99"
LICENSE="BSD-3-Clause"
PV = "1.0+git${SRCPV}"

PACKAGE_ARCH = "${MACHINE_ARCH}"

FLASH_WRITER_URL = "git://github.com/renesas-rz/rzg2_flash_writer.git"
BRANCH = "master"

SRC_URI = "${FLASH_WRITER_URL};branch=${BRANCH}"
SRCREV = "54191c580bb8cd79251c7d75b5f6ccc7f67b5dd5"
S = "${WORKDIR}/git"

SRC_URI += " \
        file://0001-flash-writer-makefile-Modify-Makefile.patch \
"

do_compile() {
        if [ ${MACHINE} == "hihope-rzg2n" ] || [ ${MACHINE} == "hihope-rzg2m" ]; then
                BOARD="HIHOPE";
        elif [ ${MACHINE} == "ek874" ]; then
                BOARD="EK874";
        fi
        cd ${S}
        oe_runmake BOARD=${BOARD}
}

do_install() {
        install -m 644 ${S}/AArch64_output/*.mot ${DEPLOY_DIR_IMAGE}
}
