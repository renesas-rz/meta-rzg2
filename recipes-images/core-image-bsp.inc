# Additional tools for support testing drivers in system
IMAGE_INSTALL_append = " \
    ethtool \
    linuxptp \
    yavta \
    e2fsprogs \
    dosfstools \
    util-linux \
    can-utils \
    iproute2 \
    alsa-utils \
    audio-init \
    usbutils \
    pciutils \
    linux-firmware \
    init-ifupdown \
    wlconf \
    init-wl18xx \
    iw \
    wpa-supplicant \
    kernel-module-wlcore-sdio \
    bt-fw \
    obexftp \
"

# Additional tools for support Tool develop
IMAGE_INSTALL_append = " \
    ckermit \
    connman \
    connman-client \
    connman-tools \
    connman-tests \
    connman-wait-online \
"
