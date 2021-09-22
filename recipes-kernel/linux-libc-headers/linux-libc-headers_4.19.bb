require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

KERNEL_URL = " \
    git://github.com/renesas-rz/rz_linux-cip.git"
BRANCH = "${@base_conditional("IS_RT_BSP", "1", "rzg2-cip54-rt21", "rzg2-cip54",d)}"
SRCREV = "${@base_conditional("IS_RT_BSP", "1", "54f39790f577c3ea72d35c89a3fde13338d42a6e", "01e5ce991ece261d717d468a9127039af9a403dc",d)}"
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
