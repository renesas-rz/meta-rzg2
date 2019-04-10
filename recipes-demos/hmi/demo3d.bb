LICENSE = "CLOSED"
DESCRIPTION = "Demo3D"

DEPENDS += " mesa gles-user-module weston wayland freetype libpng gstreamer1.0 gstreamer1.0-plugins-base"

SRC_URI = " \	
 file://Demo3D.tar.xz \
 file://Renesas.mp4 \
 file://0001-HMI-demos-demo3d-Porting-demo3d-to-VLP2.1-and-HMI-de.patch \
 file://0002-demo3d-add-h264parse-to-correct-pipeline-play-video-.patch \
 file://0003-Demo3D-Gst-change-sync-property-to-true.patch \
 file://0004-Demo3D-Video-scale-height-to-480-to-fix-greenline-is.patch \
 file://0005-Demo3D-Video-use-vspfilter-to-scaling-element.patch \
 file://0006-Demo3D-Change-detect-camera-input.patch \
"

S = "${WORKDIR}/Demo3D"

SRC = "${THISDIR}/Demo3D"

inherit pkgconfig

INSANE_SKIP_${PN} = "already-stripped"

RDEPENDS_libweston-2_append = " gles-user-module "

do_patch() {
    cd ${WORKDIR}/Demo3D
    git init
    git apply ../*.patch
}

do_compile() {
    cd ${WORKDIR}/Demo3D
    make
}

do_install() {
    install -d ${D}/home/root/Demo3D
    cp -Rf ${WORKDIR}/Demo3D/data/* ${D}/home/root/Demo3D
    install ${WORKDIR}/Demo3D/Demo3D ${D}/home/root/Demo3D/
    install ${WORKDIR}/Renesas.mp4 ${D}/home/root/Demo3D/
}

RDEPENDS_${PN} += "gles-user-module"

FILES_${PN}-dev = "${libdir}/* ${includedir}"

FILES_${PN} = "/home/root/Demo3D/* \	
               /home/root/Demo3D/pics* \
"
FILES_${PN}-dbg += " \
 /home/root/Demo3D/.debug/* \
"
