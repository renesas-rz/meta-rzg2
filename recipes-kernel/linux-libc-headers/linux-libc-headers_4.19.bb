require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

KERNEL_URL = " \
    git://github.com/renesas-rz/rz_linux-cip.git"
BRANCH = "${@base_conditional("IS_RT_BSP", "1", "rzg2-cip94-rt29", "rzg2-cip94",d)}"
SRCREV = "${@base_conditional("IS_RT_BSP", "1", "972c0e038a92c9bb8475f01f254f6ef2c6519689", "e9514f97b7ec3e749e58abbf10e7f5bcc1c752b7",d)}"
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
