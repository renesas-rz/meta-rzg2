DESCRIPTION = "Kernel module of PowerVR GPU"
LICENSE = "GPLv2 & MIT"
LIC_FILES_CHKSUM = " \
    file://GPL-COPYING;md5=60422928ba677faaa13d6ab5f5baaa1e \
    file://MIT-COPYING;md5=8c2810fa6bfdc5ae5c15a0c1ade34054 \
"
inherit module
require include/rzg2-modules-common.inc
PN = "kernel-module-gles"
PR = "r0"

COMPATIBLE_MACHINE = "(r8a774a1|r8a774b1|r8a774c0)"
PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI_r8a774b1 = 'file://GSX_KM_M3N.tar.bz2'
SRC_URI_r8a774a1 = 'file://GSX_KM_M3.tar.bz2'
SRC_URI_r8a774c0 = 'file://GSX_KM_E3.tar.bz2'

SRC_URI_append = "\
	file://0001-supporting-kernel-version-4.19-and-later.patch \
"

S = "${WORKDIR}/rogue_km"

KBUILD_DIR_r8a774b1 = "${S}/build/linux/r8a77965_linux"
KBUILD_DIR_r8a774a1 = "${S}/build/linux/r8a7796_linux"
KBUILD_DIR_r8a774c0 = "${S}/build/linux/r8a7799_linux"
KBUILD_OUTDIR_r8a774b1 = "binary_r8a77965_linux_release/target_aarch64/kbuild/"
KBUILD_OUTDIR_r8a774a1 = "binary_r8a7796_linux_release/target_aarch64/kbuild/"
KBUILD_OUTDIR_r8a774c0 = "binary_r8a7799_linux_release/target_aarch64/kbuild/"

B = "${KBUILD_DIR}"

EXTRA_OEMAKE = "KERNELDIR=${STAGING_KERNEL_BUILDDIR}"
EXTRA_OEMAKE += "CROSS_COMPILE=${CROSS_COMPILE}"

# Build GFX kernel module without suffix
KERNEL_MODULE_PACKAGE_SUFFIX = ""

do_fetch[file-checksums] = ""

module_do_compile() {
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
    cd ${KBUILD_DIR}
    oe_runmake
}

module_do_install() {
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
    install -d ${D}/lib/modules/${KERNEL_VERSION}
    cd ${KBUILD_DIR}
    oe_runmake DISCIMAGE="${D}" install
}

# Ship the module symbol file to kernel build dir
SYSROOT_PREPROCESS_FUNCS = "module_sysroot_symbol"

module_sysroot_symbol() {
    install -m 644 ${S}/${KBUILD_OUTDIR}/Module.symvers ${STAGING_KERNEL_BUILDDIR}/GLES.symvers
}

# Clean up the module symbol file
CLEANFUNCS = "module_clean_symbol"

module_clean_symbol() {
    rm -f ${STAGING_KERNEL_BUILDDIR}/GLES.symvers
}

FILES_${PN} = " \
    /lib/modules/${KERNEL_VERSION}/extra/pvrsrvkm.ko \
    ${sysconfdir}/modules-load.d \
"

RPROVIDES_${PN} += "kernel-module-pvrsrvkm kernel-module-dc-linuxfb"

# Auto load pvrsrvkm
KERNEL_MODULE_AUTOLOAD_append = " pvrsrvkm"
