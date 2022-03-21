require recipes-kernel/linux/linux-renesas.inc

LINUX_VERSION ?= "5.10.x"
KERNEL_VERSION_SANITY_SKIP="1"

BRANCH = "rvc/rz-five-linux-5.10.83-cip1"
SRCREV="28d74760641fbc52e5da2d95ce45382e7be7176b"

# User can set local git folder:
# SRC_URI = "git:///local/host/git/source/dir;branch=${BRANCH}"
SRC_URI = " \
 	git://github.com/renesas-rz/rzg_linux-cip_private.git;protocol=https;branch=${BRANCH} \
"
