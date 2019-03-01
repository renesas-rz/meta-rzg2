LICENSE = "CLOSED"
DESCRIPTION = "This is configuration for environments"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI = " \
 file://RZ_scripts.tar.xz  \
 file://ATT93936.bashrc \
 file://0001-Add-CAM_DEV-variable-to-camera-encoding-script.patch \
 file://0002-HMI-fix-issue-video-file-encoded-too-fast.patch \
 file://0003-HMI-encoding-app-support-LVDS-resolution.patch \
 file://0004-HMI-network-previewing-app-support-LVDS-resolution.patch \
 file://0005-adding-scaling-function-for-video-playback.patch \
 file://0006-demos-Add-more-element-h264parse-to-video_playback-a.patch \
 file://0007-HMI-demos-demos-encoded-Change-the-properties-of-vspmfil.patch \
 file://0008-HMI-Video_Playback-Remove-properties-out-width-and-o.patch \
 file://0009-HMI-demos-demos-set-width-and-height-in-capsfilter-i.patch \
 file://0010-demos-streaming-add-h264parse-to-correct-pipeline-st.patch \
 file://0011-demos-remove-out-width-and-out-height-from-RZ_GST-ne.patch \
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
