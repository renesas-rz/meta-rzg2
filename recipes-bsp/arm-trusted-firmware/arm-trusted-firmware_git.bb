DESCRIPTION = "ARM Trusted Firmware"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = " \
   file://${WORKDIR}/git/docs/license.rst;md5=189505435dbcdcc8caa63c46fe93fa89 \
   file://${WORKDIR}/mbedtls/LICENSE;md5=302d50a6369f5f22efdb674db908167a \
"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit deploy
require include/multimedia-control.inc
require include/ecc-control.inc

S = "${WORKDIR}/git"

BRANCH = "master"
BRANCH_mbedtls = "mbedtls-2.16"

SRC_URI = " \
	git://git.trustedfirmware.org/TF-A/trusted-firmware-a.git;branch=${BRANCH};protocol=https \
	git://github.com/ARMmbed/mbedtls.git;branch=${BRANCH_mbedtls};name=mbedtls;destsuffix=mbedtls \
"

#Tag v2.4
SRCREV = "e2c509a39c6cc4dda8734e6509cdbe6e3603cdfc"
SRCREV_mbedtls = "04a049bda1ceca48060b57bc4bcf5203ce591421"

SRC_URI += " \
	file://0001-rzg-initial-commit-for-the-RZG-gen2-boards.patch \
	file://0002-rzg-drivers-add-staging-drivers.patch \
	file://0003-rzg-drivers-authentication.patch \
	file://0004-rzg-drivers-add-board-identification.patch \
	file://0005-rzg-drivers-add-delay-driver.patch \
	file://0006-rzg2-drivers-add-dma-support.patch \
	file://0007-rzg-drivers-add-emmc-support.patch \
	file://0008-rzg-drivers-add-i2c-for-dvfs.patch \
	file://0009-rzg-drivers-add-io-drivers-for-emmc-mem.patch \
	file://0010-rzg-drivers-add-console-and-serial-controller-interf.patch \
	file://0011-plat-rzg-add-common-file.patch \
	file://0012-rzg-drivers-add-power-controller-driver.patch \
	file://0013-rzg-drivers-add-rom-api.patch \
	file://0014-rzg-drivers-SPI-multi-I-O-bus-controller.patch \
	file://0015-rzg-drivers-add-watchdog-support.patch \
	file://0016-plat-renesas-rzg-Add-ECC-support-for-RZ-G2-platform.patch \
	file://0018-rzg-plat-Zero-terminate-the-string-in-unsigned_num_p.patch \
	file://0019-Don-t-return-error-information-from-console_flush.patch \
	file://0020-rzg-drivers-console-Treat-log-as-device-memory.patch \
	file://0021-rzg-ddr-Update-DDR-setting-for-G2H-G2M-and-G2N.patch \
	file://0022-plat-rzg-Update-IPL-and-Secure-Monitor-Rev.2.0.7.patch \
	file://0023-rzg-g2e-add-support-for-identifying-board-revision.patch \
	file://0024-drivers-renesas-rzg-wdt-Create-init-counter-function.patch \
	file://0025-plat-rzg-bl2_fusa-re-work-initialize-memory-of-FUSA-.patch \
	file://0026-plat-renesas-Implement-SiP-Service-Call.patch \
"

PV = "v2.4+git"

COMPATIBLE_MACHINE = "(ek874|hihope-rzg2m|hihope-rzg2n|hihope-rzg2h)"
PLATFORM = "rzg"
ATFW_OPT_LOSSY = "${@base_conditional("USE_MULTIMEDIA", "1", "RZG_LOSSY_ENABLE=1", "", d)}"
ATFW_OPT_r8a774c0 = "LSI=G2E RZG_SA0_SIZE=0 RZG_DRAM_DDR3L_MEMCONF=1 RZG_DRAM_DDR3L_MEMDUAL=1 SPD="none""
ATFW_OPT_r8a774a1 = "LSI=G2M RZG_DRAM_SPLIT=2 SPD="none""
ATFW_OPT_r8a774b1 = "LSI=G2N SPD="none""
ATFW_OPT_r8a774e1 = "LSI=G2H RZG_DRAM_SPLIT=2 RZG_DRAM_LPDDR4_MEMCONF=1 RZG_DRAM_CHANNEL=5 SPD="none""

ATFW_OPT_append_r8a774c0 = "${@base_conditional("USE_ECC", "1", " LIFEC_DBSC_PROTECT_ENABLE=0 RZG_DRAM_ECC=1 ", "",d)}"

ATFW_OPT_append_r8a774a1 = "${@base_conditional("USE_ECC", "1", " LIFEC_DBSC_PROTECT_ENABLE=0 RZG_DRAM_SPLIT=0 RZG_DRAM_ECC=1 ", " ${ATFW_OPT_LOSSY} ",d)}"

ATFW_OPT_append_r8a774b1 = "${@base_conditional("USE_ECC", "1", " LIFEC_DBSC_PROTECT_ENABLE=0 RZG_DRAM_ECC=1 ", " ${ATFW_OPT_LOSSY} ",d)}"

ATFW_OPT_append_r8a774e1 = "${@base_conditional("USE_ECC", "1", " LIFEC_DBSC_PROTECT_ENABLE=0 RZG_DRAM_SPLIT=0 RZG_DRAM_ECC=1 ", " ${ATFW_OPT_LOSSY} ",d)}"

ATFW_OPT_append += " RZG_DRAM_ECC_FULL=${ECC_FULL} "
ATFW_OPT_append += " RZG_RPC_HYPERFLASH_LOCKED=0 MBEDTLS_DIR=../mbedtls "

# requires CROSS_COMPILE set by hand as there is no configure script
export CROSS_COMPILE="${TARGET_PREFIX}"

# Let the Makefile handle setting up the CFLAGS and LDFLAGS as it is a standalone application
CFLAGS[unexport] = "1"
LDFLAGS[unexport] = "1"
AS[unexport] = "1"
LD[unexport] = "1"

do_compile() {
    oe_runmake distclean
    oe_runmake bl2 bl31 rzg PLAT=${PLATFORM} ${ATFW_OPT}
}

# do_install() nothing
do_install[noexec] = "1"

do_deploy() {
    # Create deploy folder
    install -d ${DEPLOYDIR}

    # Copy IPL to deploy folder
    install -m 0644 ${S}/build/${PLATFORM}/release/bl2/bl2.elf ${DEPLOYDIR}/bl2-${MACHINE}.elf
    install -m 0644 ${S}/build/${PLATFORM}/release/bl2.bin ${DEPLOYDIR}/bl2-${MACHINE}.bin
    install -m 0644 ${S}/build/${PLATFORM}/release/bl2.srec ${DEPLOYDIR}/bl2-${MACHINE}.srec
    install -m 0644 ${S}/build/${PLATFORM}/release/bl31/bl31.elf ${DEPLOYDIR}/bl31-${MACHINE}.elf
    install -m 0644 ${S}/build/${PLATFORM}/release/bl31.bin ${DEPLOYDIR}/bl31-${MACHINE}.bin
    install -m 0644 ${S}/build/${PLATFORM}/release/bl31.srec ${DEPLOYDIR}/bl31-${MACHINE}.srec
    install -m 0644 ${S}/tools/renesas/rzg_layout_create/bootparam_sa0.srec ${DEPLOYDIR}/bootparam_sa0.srec
    install -m 0644 ${S}/tools/renesas/rzg_layout_create/cert_header_sa6.srec ${DEPLOYDIR}/cert_header_sa6.srec
}

addtask deploy before do_build after do_compile
