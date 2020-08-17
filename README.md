# meta-rzg2

This is a Yocto build layer that provides support for the RZ/G2 Group of 64bit Arm-based MPUs from Renesas Electronics.
Currently the following boards and MPUs are supported:

- Board: EK874 / MPU: R8A774C0 (RZ/G2E)
- Board: HIHOPE-RZG2M / MPU: R8A774A1 (RZG2M)
- Board: HIHOPE-RZG2N / MPU: R8A774B1 (RZG2N)
- Board: HIHOPE-RZG2H / MPU: R8A774E1 (RZG2H)

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
            meta-filesystems, meta-networking, meta-python (for Docker)
    branch: rocko
    revision: 352531015014d1957d6444d114f4451e241c4d23
    
    URI:http://git.yoctoproject.org/cgit.cgi/meta-gplv2/
    layers: meta-gplv2
    branch: rocko
    revision: f875c60ecd6f30793b80a431a2423c4b98e51548

    (for core-image-qt)
    URI: https://github.com/meta-qt5/meta-qt5.git 
    revision: c1b0c9f546289b1592d7a895640de103723a0305

    (for Docker)
    URI: https://git.yoctoproject.org/git/meta-virtualization
    branch: rocko
    revision: b704c689b67639214b9568a3d62e82df27e9434f

## Build Instructions

The following instructions require a Poky installation (or equivalent).

Below git configuration is required:
```bash
    $ git config --global user.email "you@example.com"
    $ git config --global user.name "Your Name"
```

Download proprietary graphics and multimedia drivers from Renesas.

To download Multimedia and Graphics library and related Linux drivers, please use the following link:

    English: https://www.renesas.com/us/en/products/rzg-linux-platform/rzg-marcketplace.html
    Japanese: https://www.renesas.com/jp/ja/products/rzg-linux-platform/rzg-marcketplace.html

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
\<board\> : ek874, hihope-rzg2m, hihope-rzg2n, hihope-rzg2h

\<toolchain\> : poky-gcc, linaro-gcc

**(Optional)** If you would like to enable Docker feature, please uncomment the below line inside file _"./conf/local.conf"_:
```
    #MACHINE_FEATURES_append = " docker"
```

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
    $ bitbake core-image-weston(|qt)-sdk -c populate_sdk
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

## Build configs

It is possible to change some build configs as below:
* GPLv3: choose to not allow, or allow, GPLv3 packages
  * **Non-GPLv3 (default):** not allow GPLv3 license. All recipes that has GPLv3 license will be downgrade to older version that has alternative license (done by meta-gplv2). In this setting customer can ignore the risk of strict license GPLv3
  ```
  INCOMPATIBLE_LICENSE = "GPLv3 GPLv3+"
  ```
  * Allow-GPLv3: allow GPLv3 license. If user is fine with strict copy-left license GPLv3, can use this setting to get newer software version.
  ```
  #INCOMPATIBLE_LICENSE = "GPLv3 GPLv3+"
  ```
* CIP Core: choose the version of CIP Core to build with. CIP Core are software packages that are maintained for long term by CIP community.
  * **Buster-limited (default)**: use limited packages from CIP Core Buster
  ```
  MACHINE_FEATURES_append = " Buster-limited"
  ```
  * Buster-full: use as many packages from CIP Core Buster as possible. Note that currently GPLv3 must be allowed for building Buster-full.
  ```
  #INCOMPATIBLE_LICENSE = "GPLv3 GPLv3+"
  MACHINE_FEATURES_append = " Buster-full"
  ```
  * Jessie: not use CIP Core Buster, use limited packages from CIP Core Jessie instead
  ```
  #MACHINE_FEATURES_append = " Buster-limited"
  ```
  * None CIP Core: not use CIP Core at all, use all default version from Yocto 2.4 Rocko
  ```
  #INCOMPATIBLE_LICENSE = "GPLv3 GPLv3+"
  #MACHINE_FEATURES_append = " Buster-limited"
  #BINUVERSION = "${@'2.31.1' if 'Buster' in '${MACHINE_FEATURES}' else '2.25'}"
  #PREFERRED_VERSION_nativesdk-binutils = "${BINUVERSION}"
  #GLIBCVERSION = "${@'2.28' if 'Buster' in '${MACHINE_FEATURES}' else '2.19'}"
  #PREFERRED_VERSION_nativesdk-glibc-locale = "${GLIBCVERSION}"
  #BBMASK .= "|glibc-mtrace"
  #RDEPENDS_${PN}_remove_pn-packagegroup-core-tools-debug = "libc-mtrace"
  #DEPENDS_remove_pn-openssh = "openssl10"
  #DEPENDS_append_pn-openssh = " openssl"
  BBMASK .= "|recipes-cip-core"
  ```
Below table show the version of recipes that change due to above setting.  
Note that this table only show packages that change version, others are not shown.  
Package versions noted with `(debian)` mean they are CIP Core packages. The source code is taken from Debian repo. Different with original version, Debian version has many bug fixes backported from newer version.

|                        | **Buster-limited (non-GPLv3)** | Buster-full (allow GPLv3) | Jessie (non-GPLv3) | None CIP Core (allow GPLv3) |
|------------------------|--------------------------------|---------------------------|--------------------|-----------------------------|
| busybox                |  1.30.1 (debian)               |  1.30.1 (debian)          | 1.22   (debian)    | 1.24.1                      |
| openssl                |  1.1.1d (debian)               |  1.1.1d (debian)          | 1.0.1t (debian)    | 1.0.2o                      |
| glibc                  |  2.28   (debian)               |  2.28   (debian)          | 2.19   (debian)    | 2.26                        |
| binutils               |  -                             |  2.31.1 (debian)          | -                  | -                           |
| coreutils              |  6.9                           |  8.30   (debian)          | 6.9                | 8.27                        |
| gnupg                  |  1.4.7                         |  2.2.12 (debian)          | 1.4.7              | 2.2.0                       |
| libassuan              |  2.4.3                         |  2.5.2  (debian)          | 2.4.3              | 2.4.3                       |
| libpam                 |  1.3.0                         |  1.3.1  (debian)          | 1.3.0              | 1.3.0                       |
| libgcrypt              |  1.7.3                         |  1.8.4  (debian)          | 1.7.3              | 1.7.3                       |
| libunistring           |  0.9.7                         |  0.9.10 (debian)          | 0.9.7              | 0.9.7                       |
| perl                   |  5.24.1                        |  5.28.1 (debian)          | 5.24.1             | 5.24.1                      |
| bash                   |  3.2.57                        |  4.4                      | 3.2.57             | 4.4                         |
| diffutils              |  -                             |  3.6                      | -                  |                             |
| dosfstools             |  2.11                          |  4.1                      | 2.11               | 4.1                         |
| gawk                   |                                |  4.1.4                    | 3.1.5              | 4.1.4                       |
| m4                     |                                |  1.4.18                   | 1.4.9              | 1.4.18                      |
| make                   |  3.81                          |  4.2.1                    | 3.81               | 4.2.1                       |
| sed                    |  4.1.2                         |  4.2.2                    | 4.1.2              | 4.2.2                       |

binutils is an optional package, and will not be added to core-image at default, except in Buster-full (allow GPLv3) setting.  
Besides, due to the license GPLv3, binutils cannot be added to core-image with non-GPLv3 setting
