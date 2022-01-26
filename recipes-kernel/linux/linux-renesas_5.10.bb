require recipes-kernel/linux/linux-renesas.inc
#FILESEXTRAPATHS:prepend := "${THISDIR}/files/ae350-ax45mp:"

LINUX_VERSION ?= "5.10.x"
KERNEL_VERSION_SANITY_SKIP="1"

BRANCH = "hifive-unmatched-cip510"
SRCREV="57d0d0b9b76d13e8b57eb947c4038fade4e08081"

# User can set local git folder:
# SRC_URI = "git:///local/host/git/source/dir;branch=${BRANCH}"
SRC_URI = " \
 	https://github.com/renesas-rz/rzg_linux-cip_private.git;branch=${BRANCH} \
"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

COMPATIBLE_MACHINE = "(rzfive-dev)"

KERNEL_FEATURES:remove = "cfg/fs/vfat.scc"
