require recipes-graphics/images/core-image-weston.bb
require core-image-renesas-base.inc
require core-image-bsp.inc
require core-image-weston.inc
require core-image-hmi.inc

LICENSE = "CLOSED"

export SOURCE_DIR="${THISDIR}/${PN}"

setup_weston () {
    cp ${SOURCE_DIR}/weston.ini ${IMAGE_ROOTFS}/etc/xdg/weston/weston.ini 
}

ROOTFS_POSTPROCESS_COMMAND_append = "setup_weston;"
