require recipes-graphics/images/core-image-weston.bb
require core-image-renesas-base.inc
require core-image-bsp.inc
require core-image-weston.inc

IMAGE_INSTALL_append = " \
     packagegroup-qt5-examples \
"
