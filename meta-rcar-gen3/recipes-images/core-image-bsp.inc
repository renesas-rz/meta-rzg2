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
    usbutils \
    pciutils \
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
