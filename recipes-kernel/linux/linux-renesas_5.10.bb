require recipes-kernel/linux/linux-renesas.inc

LINUX_VERSION ?= "5.10.x"
KERNEL_VERSION_SANITY_SKIP="1"

BRANCH = "rzfive-5.10-cip1"
SRCREV="6e3c1295d28c57cc2f4b511e166f0214df83b547"

# User can set local git folder:
# SRC_URI = "git:///local/host/git/source/dir;branch=${BRANCH}"
SRC_URI = " \
 	git://github.com/renesas-rz/rz_linux-cip.git;protocol=https;branch=${BRANCH} \
	file://0001-this-time-try-to-make-the-kernel-to-set-the-PMA-inst.patch \
	file://0002-dts-renesas-r9a07g043f01-dev-smarc-set-cma-mem-size.patch \
	file://0003-noncache_dma-dma_direct_alloc_page-use-addtrs.patch \
"
