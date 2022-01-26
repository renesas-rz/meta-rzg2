#@TYPE: Machine
#@NAME: rz-rv64 
#@SOC: RZ RISC-V 64-bit Group
#@DESCRIPTION: Machine configuration for all RZ RISC-V 64-bit LSI
#@MAINTAINER: Nhan Nguyen <nhan.nguyen.yb@renesas.com>

require conf/machine/include/riscv/tune-rz-rv64.inc

KERNEL_CLASSES = "kernel-fitimage"
KERNEL_IMAGETYPE = "Image"

PREFERRED_PROVIDER_virtual/kernel ?= "linux-renesas"
PREFERRED_PROVIDER_virtual/bootloader ?= "u-boot"

PREFERRED_VERSION_openocd-native = "riscv"
PREFERRED_VERSION_openocd = "riscv"

# Platform setting to build OpenSBI. TODO: edit
RISCV_SBI_PLAT = "renesas/rzf-dev"

## This sets u-boot as the default OpenSBI payload
RISCV_SBI_PAYLOAD ?= "u-boot.bin"

SERIAL_CONSOLES = "115200;ttySC0"

MACHINE_EXTRA_RRECOMMENDS += " kernel-modules"

IMAGE_FSTYPES += " ext4 tar.bz2"
KERNEL_DEVICETREE ?= " \
	renesas/r9a07g043f-dev.dtb \
"

## Do not update fstab file when using wic images
WIC_CREATE_EXTRA_ARGS ?= "--no-fstab-update"

UBOOT_MACHINE = "rzf-dev_ddr4_defconfig"

# U-Boot SPL
SPL_BINARY ?= "spl/u-boot-spl.bin"

# U-Boot ITB
UBOOT_SUFFIX ?= "itb"

# boot partition
IMAGE_BOOT_FILES ?= " \
    u-boot.itb \
    u-boot-spl.bin \
    fip.srec \
"

MACHINE_FEATURES = "screen keyboard ext2 ext3 ext4serial"

EXTRA_IMAGEDEPENDS += " opensbi u-boot"

