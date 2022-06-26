require recipes-kernel/linux/linux-renesas.inc

LINUX_VERSION ?= "5.10.x"
KERNEL_VERSION_SANITY_SKIP="1"

BRANCH = "rzfive-5.10-cip1"
SRCREV="9463bb265fcc05e1344da1e12fb1454da4cc1153"

# User can set local git folder:
# SRC_URI = "git:///local/host/git/source/dir;branch=${BRANCH}"
SRC_URI = " \
 	git://github.com/renesas-rz/rz_linux-cip.git;protocol=https;branch=${BRANCH} \
"

do_deploy_append() {
	for dtbf in ${KERNEL_DEVICETREE}; do
		dtb=`normalize_dtb "$dtbf"`
		dtb_ext=${dtb##*.}
		dtb_base_name=`basename $dtb .$dtb_ext`
		for type in ${KERNEL_IMAGETYPE_FOR_MAKE}; do
			ln -sf $dtb_base_name-${KERNEL_DTB_NAME}.$dtb_ext $deployDir/$type-$dtb_base_name.$dtb_ext
		done
	done
}
