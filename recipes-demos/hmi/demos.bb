LICENSE = "CLOSED"
DESCRIPTION = "This is configuration for environments"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI = " \
 file://RZ_scripts.tar.xz  \
 file://ATT93936.bashrc \
 file://0001-Add-CAM_DEV-variable-to-camera-encoding-script.patch \
 file://0002-PlayBack-Replace-pipeline-to-compatible-with-RCar-Ge.patch \
 file://0003-Network-receive-streaming-Scale-fix-to-display.patch \
 file://0004-Network-transmit-video-Fix-condition.patch \
 file://0005-Network-video-wireless-streaming-Remove-unavailable-.patch \
 file://0006-hmi-demos-Change-videoconvert-of-camera-preview-demo.patch \
 file://0007-hmi-demos-encoded-review-Remove-position-on-waylands.patch \
 file://0008-hmi-demos-encoded-preview-Change-videoconvert-to-com.patch \
 file://0009-hmi-demos-setup-wireless-Fix-condition.patch \
 file://0010-hmi-demos-encoded-preview-Add-h264parse-element-for-.patch \
 file://0011-hmi-demos-encoded-preview-change-order-of-element-du.patch \
 file://0012-hmi-demos-Encode-Preview-change-size-of-display-vide.patch \
 file://0013-hmi-demos-encode-preview-add-property-dmabuf.patch \
"

do_patch () {
	cd ${S}/../RZ_scripts
	git apply ../*.patch
	chmod 755 ${S}/../RZ_scripts -Rf
}
do_install () {
	install -d ${D}/${ROOT_HOME}/RZ_scripts
	cp ${S}/../RZ_scripts/* ${D}/${ROOT_HOME}/RZ_scripts
	cp ${S}/../ATT93936.bashrc ${D}/${ROOT_HOME}/.bashrc
}

INSANE_SKIP_${PN} += "ldflags debug-files file-rdeps"
#do_patch[noexec] = "1"
do_cofigure[noexec] = "1"
do_compile[noexec] = "1"

PACKAGES = "${PN}"

FILES_${PN} = " /* "
