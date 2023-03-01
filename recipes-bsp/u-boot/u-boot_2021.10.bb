require u-boot-common_${PV}.inc
require u-boot.inc

DEPENDS += "bc-native dtc-native"

UBOOT_URL = "git://github.com/renesas-rz/renesas-u-boot-cip.git"
BRANCH = "v2021.10/rzg2"

SRC_URI = "${UBOOT_URL};branch=${BRANCH}"
SRCREV = "22ccd65ae1f645a3ed5bfd8e80e27a3af15f9243"
PV = "v2021.10+git${SRCPV}"

UBOOT_SREC_SUFFIX = "srec"
UBOOT_SREC ?= "u-boot-elf.${UBOOT_SREC_SUFFIX}"
UBOOT_SREC_IMAGE ?= "u-boot-elf-${MACHINE}-${PV}-${PR}.${UBOOT_SREC_SUFFIX}"
UBOOT_SREC_SYMLINK ?= "u-boot-elf-${MACHINE}.${UBOOT_SREC_SUFFIX}"

SRC_URI_append = " \
	file://asr/0001-efi_loader-Fix-loaded-image-alignment.patch                    \
	file://asr/0002-hihope_rzg2_defconfig-enable-configure-suggested-for.patch     \
	file://asr/0003-rcar-gen3-common.h-increase-limit-size-for-Uboot.patch         \
	file://asr/0004-configs-Disable-Watchdog.patch                                 \
	file://asr/0005-arm-dts-update-dts-from-current-upstream-kernel.patch          \
	file://asr/0006-hihope_rzg2-Enable-distro_bootcmd.patch                        \
	file://asr/0007-configs-hihope_rzg2_defconfig-Correct-SYS_MMC_ENV_DE.patch     \
	file://asr/0008-configs-hihope_rzg2_defconfig-enable-NET_RANDOM_ETHA.patch     \
	file://asr/0009-configs-hihope_rzg2_defconfig-Enable-CONFIG_CMD_RTC.patch      \
	file://asr/0010-drivers-rtc-emul_rtc-Store-RTC-s-time-to-rtc_emul_ep.patch     \
	file://asr/0011-include-configs-rcar-gen3-common.h-Update-automate-b.patch     \
	file://asr/0012-include-configs-rcar-gen3-common.h-Add-usb_pgood_del.patch     \
	file://asr/0013-include-configs-rcar-gen3-common.h-W-A-Second-starti.patch     \
	file://asr/0014-env-Refactor-the-way-to-get-needed-variable.patch              \
	file://asr/0015-arch-arm-dts-r8a774a1-Update-armv8-timer-node.patch            \
	file://asr/0016-arch-arm-dts-r8a774a1-hihope-rzg2m-u-boot-Remove-HS4.patch     \
	file://asr/0017-Serial-flash-capability-enable-for-highhope-RZ-G2M.patch       \
	file://asr/0018-RZ-G2M-hihope-u-boot-allowed-to-be-updated-from-UEFI.patch     \
	file://asr/0019-RZ-G2M-hihope-Disable-Idle-state-binding.patch                 \
"
do_deploy_append() {
    if [ -n "${UBOOT_CONFIG}" ]
    then
        for config in ${UBOOT_MACHINE}; do
            i=$(expr $i + 1);
            for type in ${UBOOT_CONFIG}; do
                j=$(expr $j + 1);
                if [ $j -eq $i ]
                then
                    install -m 644 ${B}/${config}/${UBOOT_SREC} ${DEPLOYDIR}/u-boot-elf-${type}-${PV}-${PR}.${UBOOT_SREC_SUFFIX}
                    cd ${DEPLOYDIR}
                    ln -sf u-boot-elf-${type}-${PV}-${PR}.${UBOOT_SREC_SUFFIX} u-boot-elf-${type}.${UBOOT_SREC_SUFFIX}
                fi
            done
            unset j
        done
        unset i
    else
        install -m 644 ${B}/${UBOOT_SREC} ${DEPLOYDIR}/${UBOOT_SREC_IMAGE}
        cd ${DEPLOYDIR}
        rm -f ${UBOOT_SREC} ${UBOOT_SREC_SYMLINK}
        ln -sf ${UBOOT_SREC_IMAGE} ${UBOOT_SREC_SYMLINK}
        ln -sf ${UBOOT_SREC_IMAGE} ${UBOOT_SREC}
    fi
}
