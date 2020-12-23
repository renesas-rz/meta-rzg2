require recipes-graphics/images/core-image-weston.bb
require core-image-renesas-base.inc
require core-image-bsp.inc
require core-image-weston.inc

inherit populate_sdk_qt5_base

DESCRIPTION = "Image with weston and qt support that includes everything \
within core-image-qt plus meta-toolchain, development headers and libraries to \
form a standalone SDK."

IMAGE_FEATURES += " \
    dev-pkgs tools-sdk \
    tools-debug debug-tweaks \
    ssh-server-openssh \
"

IMAGE_INSTALL_append = " kernel-devsrc"

# Add some necessary tool
TOOLCHAIN_HOST_TASK_append = " nativesdk-bison nativesdk-flex nativesdk-python3-pycryptodome nativesdk-python3-pycryptodomex"

# Post process after installed sdk
sdk_post_process () {
	# Set up kernel for building kernel config now
	echo "configuring scripts of kernel source for building .ko file..."
	$SUDO_EXEC bash -c 'source "$0" && cd "${OECORE_TARGET_SYSROOT=}/usr/src/kernel" && make scripts' $target_sdk_dir/environment-setup-@REAL_MULTIMACH_TARGET_SYS@
}
SDK_POST_INSTALL_COMMAND_append = " ${sdk_post_process}"

export SOURCE_DIR="${THISDIR}/environment-setup"
fakeroot append_setup () {
    install -d ${SDK_OUTPUT}/${SDKPATH}/sysroots/${SDK_SYS}/environment-setup.d/
    cp ${SOURCE_DIR}/environment-setup-append.sh ${SDK_OUTPUT}/${SDKPATH}/sysroots/${SDK_SYS}/environment-setup.d/
}
SDK_POSTPROCESS_COMMAND_prepend = " append_setup;"


### For cross-compile Qt ###
TOOLCHAIN_HOST_TASK_append = ' nativesdk-qtbase-tools '

# Create pri file to prevent below warning when running qmake
#   "...usr/lib/qt5/mkspecs/oe-device-extra.pri: No such file or directory"
fakeroot append_qt () {
	# Generate oe-device-extra.pri
	if [ -d ${SDK_OUTPUT}/${SDKPATH}/sysroots/${TARGET_SYS}/${libdir}/${QT_DIR_NAME}/mkspecs ] &&
	   [ ! -f ${SDK_OUTPUT}/${SDKPATH}/sysroots/${TARGET_SYS}/${libdir}/${QT_DIR_NAME}/mkspecs/oe-device-extra.pri ]
	then
		touch ${SDK_OUTPUT}/${SDKPATH}/sysroots/${TARGET_SYS}/${libdir}/${QT_DIR_NAME}/mkspecs/oe-device-extra.pri
	fi
}
SDK_POSTPROCESS_COMMAND_prepend = " append_qt;"


### For self-compile Qt ###
IMAGE_INSTALL_append = ' packagegroup-qt5-toolchain-target '

setup_qt_env () {
        if [ ! -e ${IMAGE_ROOTFS}/${sysconfdir}/profile.d/setup_qt_env ]
        then
                echo 'export PATH=$PATH:/usr/bin/qt5' > ${IMAGE_ROOTFS}${sysconfdir}/profile.d/setup_qt_env
                echo 'export INCLUDEPATH=$INCLUDEPATH:/usr/include/qt5' >> ${IMAGE_ROOTFS}${sysconfdir}/profile.d/setup_qt_env
        fi
}
ROOTFS_POSTPROCESS_COMMAND += 'setup_qt_env;'

# qt multimedia needs alsa-dev when self-compiling
IMAGE_INSTALL_append = " alsa-dev "

# weston drm-backend need xkeyboard-config
IMAGE_INSTALL_append = " xkeyboard-config "
