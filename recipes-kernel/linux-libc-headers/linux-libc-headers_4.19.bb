require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

KERNEL_URL = " \
    git://github.com/renesas-rz/rz_linux-cip.git"
BRANCH = "${@base_conditional("IS_RT_BSP", "1", "rzg2-cip78-rt26", "rzg2-cip78",d)}"
SRCREV = "${@base_conditional("IS_RT_BSP", "1", "e153869721b2823fcab8ada6742150e8e941a429", "c5ccd26ce88f1c41ddb500730e81054eb26aad60",d)}"
LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

SRC_URI = "${KERNEL_URL};branch=${BRANCH}"

FILESEXTRAPATHS_prepend := "${THISDIR}/../linux/linux-renesas:"

SRC_URI_append = " \
    file://0001-linux-add-time.h-header-file-to-net_tstamp.h.patch \
"

S = "${WORKDIR}/git"

DEPENDS = "bison-native flex-native"

do_install_armmultilib_append () {
	oe_multilib_header asm/bpf_perf_event.h asm/kvm_para.h
}
