DESCRIPTION = "Renesas Linux Kernel"
SECTION = "kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${S}/COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

inherit kernel
require recipes-kernel/linux/linux-yocto.inc

LINUX_VERSION_EXTENSION = ""

PV = "${LINUX_VERSION}+git${SRCPV}"

KCONFIG_MODE="--alldefconfig"

KBUILD_DEFCONFIG = "renesas_defconfig"

COMPATIBLE_MACHINE = "(rzfive-dev|smarc-rzfive)"

KERNEL_DEVICETREE = " \
   renesas/r9a07g043f01-dev.dtb \
   renesas/r9a07g043f01-smarc.dtb \
"

