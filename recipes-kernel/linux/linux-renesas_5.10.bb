require recipes-kernel/linux/linux-renesas.inc

LINUX_VERSION ?= "5.10.x"
KERNEL_VERSION_SANITY_SKIP="1"

BRANCH = "rzfive-5.10-cip1"
SRCREV="f4ed4cd872798447f968241918901cb3e1d46678"

# User can set local git folder:
# SRC_URI = "git:///local/host/git/source/dir;branch=${BRANCH}"
SRC_URI = " \
 	git://github.com/renesas-rz/rz_linux-cip.git;protocol=https;branch=${BRANCH} \
"
