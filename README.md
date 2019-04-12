# meta-rzg2

This layer provides that evaluation board is mounted ARM SoCs of Renesas
Electronics, called the RZG2. Currently, this supports
board and the SoCs of the following:
- Board: EK874 / SoC: R8A774C0 (RZG2E)

## Patches

To contribute to this layer you should email patches to renesas-rz@renesas.com. Please send .patch files as email attachments, not embedded in the email body.

## Dependencies

This layer depends on:

    URI: git://git.yoctoproject.org/poky
    layers: meta, meta-yocto, meta-yocto-bsp
    branch: rocko
    revision: 7e7ee662f5dea4d090293045f7498093322802cc

    URI: git://git.linaro.org/openembedded/meta-linaro.git
    layers: meta-optee
    branch: rocko
    revision: 75dfb67bbb14a70cd47afda9726e2e1c76731885

    URI: git://git.openembedded.org/meta-openembedded
    layers: meta-oe
    branch: rocko
    revision: 352531015014d1957d6444d114f4451e241c4d23
    
    URI:http://git.yoctoproject.org/cgit.cgi/meta-gplv2/
    layers: meta-gplv2
    branch: rocko
    revision: f875c60ecd6f30793b80a431a2423c4b98e51548

    (for core-image-qt)
    URI: https://github.com/meta-qt5/meta-qt5.git 
    revision: c1b0c9f546289b1592d7a895640de103723a0305

## Build Instructions

The following instructions require a Poky installation (or equivalent).

Below git configuration is required:
```bash
    $ git config --global user.email "you@example.com"
    $ git config --global user.name "Your Name"
```

Download evaluation version of proprietary graphics and multimedia drivers from Renesas.

To download Multimedia and Graphics library and related Linux drivers, please use the following link:

    America: https://mp.renesas.com/en-us/rzg/marketplace/linux_package/index.html
    Europe : https://mp.renesas.com/en-eu/rzg/marketplace/linux_package/index.html
    Asia : https://mp.renesas.com/en-sg/rzg/marketplace/linux_package/index.html
    Japan : https://mp.renesas.com/ja-jp/rzg/marketplace/linux_package/index.html

Graphic drivers are required for Wayland. Multimedia drivers are optional.

After downloading the proprietary package, please put the package at $WORK then use copy script as below:

```bash
    $ tar -C $WORK -zxf $WORK/RZG2_Group_*_Software_Package_for_Linux_*.tar.gz
    $ export PKGS_DIR=$WORK/proprietary
    $ cd $WORK/meta-rzg2
    $ sh docs/sample/copyscript/copy_proprietary_softwares.sh -f $PKGS_DIR
    $ unset PKGS_DIR
```

Initialize a build using the 'oe-init-build-env' script in Poky. e.g.:
```bash
    $ source poky/oe-init-build-env
```

Prepare default configuration files. :
```bash
    $ cp $WORK/meta-rzg2/docs/sample/conf/<board>/<toolchain>/*.conf ./conf/
```
\<board\> : ek874

\<toolchain\> : poky-gcc, linaro-gcc

Build the target file system image using bitbake:
```bash
    $ bitbake core-image-<target>
```
\<target\>:minimal, bsp, weston, qt, hmi

After completing the images for the target machine will be available in the output
directory _'tmp/deploy/images/\<supported board name\>'_.

Images generated:
* Image (generic Linux Kernel binary image file)
* Image-\<machine name\>.dtb (DTB for target machine)
* core-image-\<target\>-\<machine name\>.tar.bz2 (rootfs tar+bzip2)
* core-image-\<target\>-\<machine name\>.ext4  (rootfs ext4 format)

## Build Instructions for SDK

Use bitbake -c populate_sdk for generating the toolchain SDK:
For 64-bit target SDK (aarch64):
```bash
    $ bitbake core-image-weston(|qt) -c populate_sdk
```
The SDK can be found in the output directory _'tmp/deploy/sdk'_

    poky-glibc-x86_64-core-image-weston(|qt)-aarch64-toolchain-x.x.sh

Usage of toolchain SDK: Install the SDK to the default: _/opt/poky/x.x_
For 64-bit target SDK:
```bash
    $ sh poky-glibc-x86_64-core-image-weston(|qt)-aarch64-toolchain-x.x.sh
```
For 64-bit application use environment script in _/opt/poky/x.x_
```bash
    $ source /opt/poky/x.x/environment-setup-aarch64-poky-linux
```
