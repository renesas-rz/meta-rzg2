DESCRIPTION = "Linux kernel for the R-Car Generation 3 based board"

require recipes-kernel/linux/linux-yocto.inc
require include/cas-control.inc

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}/:"
COMPATIBLE_MACHINE = "salvator-x|ebisu"

RENESAS_BSP_URL = "git://github.com/renesas-rz/renesas-cip.git"
BRANCH = "rcar-gen3/v4.19"
SRCREV = "0e68ba09ee8f047642aec15dc725d24e4e3a2b77"

SRC_URI = "${RENESAS_BSP_URL};protocol=https;nocheckout=1;branch=${BRANCH}"

LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"
LINUX_VERSION ?= "4.19.13"

PV = "${LINUX_VERSION}+git${SRCPV}"
PR = "r1"

KBUILD_DEFCONFIG = "defconfig"
KCONFIG_MODE = "alldefconfig"
SRC_URI_append = " \
    file://touch.cfg \
    file://gsx.cfg \
"

# Add SCHED_DEBUG config fragment to support CAS
SRC_URI_append = " \
    ${@base_conditional("USE_CAS", "1", " file://capacity_aware_migration_strategy.cfg", "",d)} \
"

# Install USB3.0 firmware to rootfs
USB3_FIRMWARE_V2 = "https://git.kernel.org/pub/scm/linux/kernel/git/firmware/linux-firmware.git/plain/r8a779x_usb3_v2.dlmem;md5sum=645db7e9056029efa15f158e51cc8a11"
USB3_FIRMWARE_V3 = "https://git.kernel.org/pub/scm/linux/kernel/git/firmware/linux-firmware.git/plain/r8a779x_usb3_v3.dlmem;md5sum=687d5d42f38f9850f8d5a6071dca3109"

SRC_URI_append = " \
    ${USB3_FIRMWARE_V2} \
    ${USB3_FIRMWARE_V3} \
    ${@bb.utils.contains('MACHINE_FEATURES','usb3','file://usb3.cfg','',d)} \
"

do_download_firmware () {
    install -m 755 ${WORKDIR}/r8a779x_usb3_v*.dlmem ${STAGING_KERNEL_DIR}/firmware
}

addtask do_download_firmware after do_configure before do_compile
