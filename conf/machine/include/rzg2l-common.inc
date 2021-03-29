require conf/machine/include/${SOC_FAMILY}.inc

# 32BIT package install (default is disable)
# This variables can be used only in multilib.
USE_32BIT_PKGS ?= "0"
USE_32BIT_WAYLAND ?= "0"
USE_32BIT_MMP ?= "0"

MACHINE_FEATURES = ""

KERNEL_IMAGETYPE = "Image"
IMAGE_FSTYPES_append = " tar.bz2 ext4"

SERIAL_CONSOLES = "115200;ttySC0"

# Device tree: support multiple dtb files for all variant RZ/G2L
KERNEL_DEVICETREE = " \
   renesas/r9a07g044l2-dev.dtb \
"

# Configuration for ARM Trusted Firmware
EXTRA_IMAGEDEPENDS += " trusted-firmware-a"

# u-boot
PREFERRED_VERSION_u-boot = "v2020.10%"
EXTRA_IMAGEDEPENDS += " u-boot"

# Add variable to Build Configuration in build log
BUILDCFG_VARS_append = " SOC_FAMILY"