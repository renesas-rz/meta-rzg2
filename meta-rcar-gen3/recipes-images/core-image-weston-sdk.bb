require recipes-graphics/images/core-image-weston.bb
require core-image-renesas-base.inc
require core-image-bsp.inc
require core-image-weston.inc

DESCRIPTION = "Image with weston support that includes everything within \
core-image-weston plus meta-toolchain, development headers and libraries to \
form a standalone SDK."

IMAGE_FEATURES += " \
    dev-pkgs tools-sdk \
    tools-debug debug-tweaks \
    ssh-server-openssh \
"

IMAGE_INSTALL_append = " kernel-devsrc ltp"

# Post process after installed sdk
sdk_post_process () {
	# Set up kernel for building kernel config now
	echo "configuring scripts of kernel source for building .ko file..."
	$SUDO_EXEC bash -c 'source "$0" && cd "${OECORE_TARGET_SYSROOT=}/usr/src/kernel" && make scripts' $env_setup_script
}
SDK_POST_INSTALL_COMMAND_append = " ${sdk_post_process}"

export SOURCE_DIR="${THISDIR}/environment-setup"
fakeroot append_setup () {
    install -d ${SDK_OUTPUT}/${SDKPATH}/sysroots/${SDK_SYS}/environment-setup.d/
    cp ${SOURCE_DIR}/environment-setup-append.sh ${SDK_OUTPUT}/${SDKPATH}/sysroots/${SDK_SYS}/environment-setup.d/
}
SDK_POSTPROCESS_COMMAND_prepend = " append_setup; "
