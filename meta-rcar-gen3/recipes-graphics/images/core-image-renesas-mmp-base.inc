require include/multimedia-control.inc
require include/omx-control.inc

# Common Multi Media Packages (MMP)
MULTIMEDIA_PKGS = " \
    packagegroup-multimedia-kernel-modules \
    packagegroup-multimedia-libs \
"

# MMP test Packages
MULTIMEDIA_TP_PKGS = " \
    vspmif-tp-user-module \
"

# OMX MMP Packages
OMX_MULTIMEDIA_PKGS = " \
    omx-user-module \
"

# Renesas MMP 32bit packages
MMP_32BIT_PKGS = " \
    ${@base_conditional("USE_MULTIMEDIA", "1", "lib32-packagegroup-multimedia-libs lib32-omx-user-module", "", d)} \
    ${@base_conditional("USE_MULTIMEDIA_TP", "1", "lib32-vspmif-tp-user-module", "", d)} \
"

# Install pkg selection
IMAGE_INSTALL_append = " \
    ${@base_conditional("USE_MULTIMEDIA", "1", " ${MULTIMEDIA_PKGS}", "", d)} \
    ${@base_conditional("USE_MULTIMEDIA_TP", "1", " ${MULTIMEDIA_TP_PKGS}", "", d)} \
    ${@base_conditional("USE_OMX_USER_MODULE", "1", " ${OMX_MULTIMEDIA_PKGS}", "", d)} \
    ${@base_conditional("USE_32BIT_MMP", "1", " ${MMP_32BIT_PKGS}", "", d)} \
"
