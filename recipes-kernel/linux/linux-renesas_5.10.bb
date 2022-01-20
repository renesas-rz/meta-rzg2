require recipes-kernel/linux/linux-renesas.inc
#FILESEXTRAPATHS:prepend := "${THISDIR}/files/ae350-ax45mp:"

LINUX_VERSION ?= "5.10.x"
KERNEL_VERSION_SANITY_SKIP="1"

BRANCH = "linux-5.10.y"
SRCREV = "v5.10.84"
SRC_URI = " \
    git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux.git;branch=${BRANCH} \
"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

SRC_URI:append = " \
    file://linux.cfg \
    "
# KBUILD_DEFCONFIG:ae350-ax45mp = "ae350_rv64_smp_defconfig"
COMPATIBLE_MACHINE = "(rzfive-dev)"

KERNEL_FEATURES:remove = "cfg/fs/vfat.scc"
