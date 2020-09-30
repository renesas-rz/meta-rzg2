DESCRIPTION = "Linux kernel for the RZG2 based board"

require recipes-kernel/linux/linux-yocto.inc
require include/cas-control.inc
require include/ecc-control.inc
require include/docker-control.inc

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}/:"
COMPATIBLE_MACHINE = "ek874|hihope-rzg2m|hihope-rzg2n|hihope-rzg2h"

KERNEL_URL = " \
    git://git.kernel.org/pub/scm/linux/kernel/git/cip/linux-cip.git"
BRANCH = "linux-4.19.y-cip"
SRCREV = "03cdb749e6350f0403badbf7931e420ea7514f56"

SRC_URI = "${KERNEL_URL};protocol=https;nocheckout=1;branch=${BRANCH}"

SRC_URI_append += "\
  file://patches.scc \
"

SRC_URI_append_r8a774c0 += "\
  ${@base_ifelse("${ECC_FULL}" != "0", " file://patches/option_patch/0001-ARM64-DTS-cat874-reduce-mem-to-960M-when-enable-DRAM.patch ", "")} \
"

SRC_URI_append_r8a774a1 += "\
  ${@base_ifelse("${ECC_FULL}" != "0", " file://patches/option_patch/0003-arm64-dts-hihope-rzg2m-Re-order-CMA-area-and-remove-.patch ", "")} \
  ${@base_ifelse("${ECC_FULL}" == "1", " file://patches/option_patch/0001-ARM64-DTS-hihope-rzg2m-disable-DRAM-channel-1-for-EC.patch \
					 file://patches/option_patch/0002-arm64-dts-r8a774a3-hihope-rzg2m-ecc-dual-Reduce-memo.patch \
					 file://patches/option_patch/0004-arm64-dts-hihope-rzg2m-enable-IPMMU-for-GSX.patch ", "")} \
  ${@base_ifelse("${ECC_FULL}" == "2", " file://patches/option_patch/0001-arm64-dts-renesas-r8a774a3-hihope-rzg2m-Reduce-memor.patch ", "")} \
"

SRC_URI_append_r8a774b1 += "\
  ${@base_ifelse("${ECC_FULL}" != "0", " file://patches/option_patch/0001-ARM64-DTS-r8a774b1-hihope-rzg2n-reduce-mem-when-enab.patch \
					 file://patches/option_patch/0002-arm64-dts-r8a774b1-hihope-rzg2n-rev2-Re-order-CMA-ar.patch ", "")} \
"

SRC_URI_append_r8a774e1 += "\
  ${@base_ifelse("${USE_ECC}" == "1",  " file://patches/option_patch/0001-arm64-dts-renesas-r8a774e1-hihope-rzg2h-Update-DRAM2.patch ", "")} \
  ${@base_ifelse("${ECC_FULL}" == "1", " file://patches/option_patch/0001-arm64-dts-renesas-r8a774e1-hihope-rzg2h-Disable-DRAM.patch \
					 file://patches/option_patch/0002-arm64-dts-r8a774e1-hihope-rzg2h-Re-order-CMA-area-an.patch \
					 file://patches/option_patch/0003-arm64-dts-r8a774e1-hihope-rzg2h-enable-IPMMU-for-GSX.patch ", "")} \
  ${@base_ifelse("${ECC_FULL}" == "2", " file://patches/option_patch/0001-arm64-dts-renesas-r8a774e1-hihope-rzg2h-Reduce-memor.patch \
					 file://patches/option_patch/0002-arm64-dts-renesas-r8a774e1-hihope-rzg2h-remove-lossy.patch ", "")} \
"

LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"
LINUX_VERSION ?= "4.19.140"

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

# Install regulatory database firmware to rootfs
REGULATORY_DB = "https://git.kernel.org/pub/scm/linux/kernel/git/sforshee/wireless-regdb.git/plain/regulatory.db?h=master-2019-06-03;md5sum=ce7cdefff7ba0223de999c9c18c2ff6f;downloadfilename=regulatory.db"
REGULATORY_DB_P7S = "https://git.kernel.org/pub/scm/linux/kernel/git/sforshee/wireless-regdb.git/plain/regulatory.db.p7s?h=master-2019-06-03;md5sum=489924336479385e2c35c21d10eb3ca2;downloadfilename=regulatory.db.p7s"

# Install Bluetooth firmware to rootfs
BLUETOOTH_FW = " https://git.ti.com/cgit/wilink8-bt/ti-bt-firmware/plain/TIInit_11.8.32.bts;md5sum=665b7c25be21933acc30dda44cfcace6;downloadfilename=TIInit_11.8.32.bts"

SRC_URI_append = " \
    ${REGULATORY_DB} \
    ${REGULATORY_DB_P7S} \
    ${BLUETOOTH_FW} \
    file://wifi.cfg \
    file://bluetooth.cfg \
"

SRC_URI_append = "\
  ${@base_conditional("USE_DOCKER", "1", " file://docker.cfg ", "", d)} \
"

do_download_firmware () {
    install -m 755 ${WORKDIR}/r8a779x_usb3_v*.dlmem ${STAGING_KERNEL_DIR}/firmware
    install -m 755 ${WORKDIR}/regulatory* ${STAGING_KERNEL_DIR}/firmware
    mkdir -p ${STAGING_KERNEL_DIR}/firmware/ti-connectivity
    install -m 755 ${WORKDIR}/TIInit_11.8.32.bts ${STAGING_KERNEL_DIR}/firmware/ti-connectivity
}

do_kernel_metadata_af_patch() {
  # need to recall do_kernel_metadata after do_patch for some patches applied to defconfig
  rm -f ${WORKDIR}/defconfig
  do_kernel_metadata
}

addtask do_download_firmware after do_configure before do_compile
addtask do_kernel_metadata_af_patch after do_patch before do_kernel_configme

# Fix race condition, which can causes configs in defconfig file be ignored
do_kernel_configme[depends] += "virtual/${TARGET_PREFIX}binutils:do_populate_sysroot"
do_kernel_configme[depends] += "virtual/${TARGET_PREFIX}gcc:do_populate_sysroot"
do_kernel_configme[depends] += "bc-native:do_populate_sysroot bison-native:do_populate_sysroot"

# Fix error: openssl/bio.h: No such file or directory
DEPENDS += "openssl-native"
