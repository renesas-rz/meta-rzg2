require recipes-bsp/u-boot/u-boot-common.inc
require recipes-bsp/u-boot/u-boot.inc
LIC_FILES_CHKSUM = "file://Licenses/README;md5=5a7450c57ffe5ae63fd732446b988025"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
DEPENDS:append = " bc dtc opensbi u-boot-tools-native"

SRCREV="f72e0c1f04635c9e0ee5f1033cb5e7bf6925aa9c"
BRANCH="v2020.10/rzf-dev"

# User can set local git folder:
# SRC_URI = "git:///local/host/git/source/dir;branch=${BRANCH}"
SRC_URI = " \
	git://github.com/renesas-rz/renesas-u-boot-cip.git;protocol=https;branch=${BRANCH} \
	file://BootLoaderHeader.bin \
    "

UBOOT_CONFIG[rzg2l-dev] = "rzfive-dev_defconfig"
UBOOT_CONFIG[smarc-rzg2l] = "smarc-rzfive_defconfig"

do_compile:prepend() {
    export OPENSBI=${DEPLOY_DIR_IMAGE}/fw_dynamic.bin
}

do_compile:append() {

	oe_runmake -C ${S} O=${B} ${UBOOT_CONFIG}
	oe_runmake -C ${S} O=${B} -j4
}

do_compile:append() {

	cat ${WORKDIR}/BootLoaderHeader.bin  ${B}/spl/u-boot-spl.bin > ${B}/u-boot-spl_bp.bin
	objcopy -I binary -O srec --adjust-vma=0x00011E00 --srec-forceS3 ${B}/u-boot-spl_bp.bin ${B}/u-boot-spl_bp-${MACHINE}.srec
	objcopy -I binary -O srec --adjust-vma=0x00011E00 --srec-forceS3 ${B}/u-boot.itb ${B}/fit-${MACHINE}.srec
}

do_deploy:append() {
    if [ -f "${WORKDIR}/boot.scr" ]; then
        install -d ${DEPLOY_DIR_IMAGE}
        install -m 755 ${WORKDIR}/boot.scr ${DEPLOY_DIR_IMAGE}
    fi
        
	#install -m 755 ${B}/u-boot-spl_bp.bin ${DEPLOY_DIR_IMAGE}
	install -m 755 ${B}/u-boot-spl_bp-${MACHINE}.srec ${DEPLOY_DIR_IMAGE}
	install -m 755 ${B}/fit-${MACHINE}.srec ${DEPLOY_DIR_IMAGE}
}

do_compile[depends] += "opensbi:do_deploy"
