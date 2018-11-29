require recipes-core/images/core-image-minimal.bb

IMAGE_FEATURES += "ssh-server-dropbear"

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
"
