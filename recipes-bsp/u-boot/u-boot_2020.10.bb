require recipes-bsp/u-boot/u-boot-common.inc
require recipes-bsp/u-boot/u-boot.inc
LIC_FILES_CHKSUM = "file://Licenses/README;md5=5a7450c57ffe5ae63fd732446b988025"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
DEPENDS:append = " bc dtc opensbi u-boot-tools-native"

SRCREV="9fc337a139c9f3c2c82b9c12b3ef82510e2aa86b"
BRANCH="v2021.12/rzf-dev"

# User can set local git folder:
# SRC_URI = "git:///local/host/git/source/dir;branch=${BRANCH}"
SRC_URI = " \
	https://github.com/renesas-rz/rzg_u-boot_private.git;branch=${BRANCH} \
	file://BootLoaderHeader.bin \
    "

do_compile:prepend:rzfive-dev() {
    export OPENSBI=${DEPLOY_DIR_IMAGE}/fw_dynamic.bin
}

do_compile:append:rzfive-dev() {

	cat ${WORKDIR}/BootLoaderHeader.bin  ${B}/spl/u-boot-spl.bin > ${B}/u-boot-spl_bp.bin
	objcopy -I binary -O srec ${B}/u-boot-spl_bp.bin ${B}/u-boot-spl_bp.srec

	objcopy -I binary -O srec ${B}/u-boot.itb ${B}/fip.srec
}

do_deploy:append:rzfive-dev() {
    if [ -f "${WORKDIR}/boot.scr" ]; then
        install -d ${DEPLOY_DIR_IMAGE}
        install -m 755 ${WORKDIR}/boot.scr ${DEPLOY_DIR_IMAGE}
    fi
        
	install -m 755 ${B}/u-boot-spl_bp.bin ${DEPLOY_DIR_IMAGE}
	install -m 755 ${B}/u-boot-spl_bp.srec ${DEPLOY_DIR_IMAGE}
	install -m 755 ${B}/fip.srec ${DEPLOY_DIR_IMAGE}
}

do_configure[depends] += "opensbi:do_deploy"
