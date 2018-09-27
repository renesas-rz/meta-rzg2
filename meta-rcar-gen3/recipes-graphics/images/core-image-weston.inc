require include/gles-control.inc
require core-image-renesas-mmp-base.inc

IMAGE_INSTALL_append_rcar-gen3 = '${@base_conditional("ENABLE_QT_FRAMEWORK", "1", \
    " packagegroup-qt5-examples", "", d)} \
'

# Gstreamer Packages
GSTREAMER_PKGS = "packagegroup-gstreamer1.0-plugins"

# Install packages for Wayland Env
# default: Basic packages, Gstreamer packages
# optional: GFX packages
IMAGE_INSTALL_append = " \
    ${GSTREAMER_PKGS} \
    packagegroup-wayland-community \
    packagegroup-graphics-renesas-proprietary \
    packagegroup-graphics-renesas-wayland \
"
