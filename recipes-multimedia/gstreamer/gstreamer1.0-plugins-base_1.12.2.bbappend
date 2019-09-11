require ../../include/multimedia-control.inc

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append += " ${@bb.utils.contains("USE_MULTIMEDIA", "1", " \
    file://0001-gstplaybin-change-vspfilter-as-default-converter.patch \
    file://0002-Add-vspfilter-replace-videoconvert.patch \
    file://0003-convertframe-change-videoconvert-to-vspfilter.patch \
    file://0004-gstplaybin-change-vspmfilter-as-default-converter.patch \
    file://0005-playback-add-set-property-dmabuf-use-for-video-filte.patch \
    ", "", d)}"
