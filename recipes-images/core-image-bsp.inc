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
    linux-firmware-ti-connectivity-license \
    linux-firmware-wl18xx \
    init-ifupdown \
    wlconf \
    init-wl18xx \
    iw \
    wpa-supplicant \
    kernel-module-wlcore-sdio \
    kernel-module-mousedev \
    bt-fw \
    obexftp \
    bluez-alsa \
    rt-tests \
    openssl \
    vin-init \
"

# Additional tools for support Tool develop
IMAGE_INSTALL_append = " \
    ckermit \
    connman \
    connman-client \
    connman-tools \
    connman-tests \
    connman-wait-online \
    lttng-modules \
    lttng-tools \
    lttng-ust \
    tcf-agent \
"
