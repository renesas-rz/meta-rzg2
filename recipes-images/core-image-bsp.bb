require recipes-core/images/core-image-minimal.bb
require core-image-renesas-base.inc
require core-image-bsp.inc
require core-image-docker.inc

IMAGE_FEATURES += "ssh-server-dropbear"
