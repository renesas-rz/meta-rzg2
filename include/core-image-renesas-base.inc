# Basic packages
IMAGE_INSTALL_append = " \
    bash \
    bonnie++ \
    v4l-utils \
    i2c-tools \
    coreutils \
    util-linux \
    busybox \
    libusb1 \
    pciutils \
    iproute2 \
    i2c-tools \
    can-utils \
    ethtool \
    iperf3 \
    usbutils \
    mtd-utils \
    dosfstools \
    e2fsprogs-badblocks \
    e2fsprogs-dumpe2fs \
    e2fsprogs-e2fsck \
    e2fsprogs-e2scrub \
    e2fsprogs-mke2fs \
    e2fsprogs-resize2fs \
    e2fsprogs-tune2fs \
    minicom \
    memtester \
    alsa-utils \
    libdrm \
    libdrm-tests \
    yavta \
    kernel-module-uvcvideo \
"

# Support secure environment
IMAGE_INSTALL_append = " \
    optee-client \
"

DOCKER_PKGS = "docker ca-certificates ntpdate kernel-module-nf-conntrack-netlink "
IMAGE_INSTALL_append = " \
    ${@oe.utils.conditional("DOCKER_ENABLE", "1", "${DOCKER_PKGS}", "", d)} \
"

update_issue() {
    # Set BSP version
    BSP_VERSION="1.4"

    # Set SoC and Board info
    case "${MACHINE}" in
    smarc-rzg2l)
      BSP_SOC="RZG2L"
      BSP_BOARD="RZG2L-SMARC-EVK"
      ;;
    rzg2l-dev)
      BSP_SOC="RZG2L"
      BSP_BOARD="RZG2L-DEV"
      ;;
    smarc-rzg2lc)
      BSP_SOC="RZG2LC"
      BSP_BOARD="RZG2LC-SMARC-EVK"
      ;;
    rzg2lc-dev)
      BSP_SOC="RZG2LC"
      BSP_BOARD="RZG2LC-DEV"
      ;;
    rzg2ul-dev)
      BSP_SOC="RZG2UL"
      BSP_BOARD="RZG2UL-DEV"
      ;;
    rzg2ul-ddr3l-dev)
      BSP_SOC="RZG2UL"
      BSP_BOARD="RZG2UL-DDR3L-DEV"
      ;;
    smarc-rzg2ul)
      BSP_SOC="RZG2UL"
      BSP_BOARD="RZG2UL-SMARC-EVK"
      ;;
    esac

    # Make issue file
    echo "BSP: ${BSP_SOC}/${BSP_BOARD}/${BSP_VERSION}" >> ${IMAGE_ROOTFS}/etc/issue
    echo "LSI: ${BSP_SOC}" >> ${IMAGE_ROOTFS}/etc/issue
    echo "Version: ${BSP_VERSION}" >> ${IMAGE_ROOTFS}/etc/issue
}
ROOTFS_POSTPROCESS_COMMAND += "update_issue; "

# Renesas Basic packages for 32bit
BASIC_32BIT_PKGS = " \
    lib32-coreutils \
    lib32-libstdc++ \
"

# Installation for 32bit packages
IMAGE_INSTALL_append = " \
    ${@oe.utils.conditional("USE_32BIT_PKGS", "1", "${BASIC_32BIT_PKGS}", "", d)} \
"
