LICENSE = "CLOSED"
DESCRIPTION = "Videos for demos"

SRC_URI = "http://resource.renesas.com/resource/lib/img/products/media/auto-j/microcontrollers-microprocessors/rz/rzg/hmi-mmpoc-videos/h264-fhd-60.mp4;name=video1 \
           http://resource.renesas.com/resource/lib/img/products/media/auto-j/microcontrollers-microprocessors/rz/rzg/hmi-mmpoc-videos/h264-wvga-30.mp4;name=video2 \
           http://resource.renesas.com/resource/lib/img/products/media/auto-j/microcontrollers-microprocessors/rz/rzg/hmi-mmpoc-videos/h264-bl10-fhd-30p-5m-aac-lc-stereo-124k-48000x264.mp4;name=video3 \
           http://resource.renesas.com/resource/lib/img/products/media/auto-j/microcontrollers-microprocessors/rz/rzg/hmi-mmpoc-videos/h264-vga-24.mp4;name=video4 \
           http://resource.renesas.com/resource/lib/img/products/media/auto-j/microcontrollers-microprocessors/rz/rzg/hmi-mmpoc-videos/h264-hd-30.mp4;name=video5 "

SRC_URI[video1.md5sum] = "bb827df771f78ca8b0b35a58d24ad3ae"
SRC_URI[video1.sha256sum] = "a625e1b48aa5cae4f85eac9b073ef84259a42b7b7941a7c394d87b8e7da12283"
SRC_URI[video2.md5sum] = "d5d4988a9bfcd66d94a2d2f6a73cec36"
SRC_URI[video2.sha256sum] = "e6edd7c4830c0c5171b06b286599839a32123d0ed52bfc017c884385816f174f"
SRC_URI[video3.md5sum] = "33696ae57ae684e2cc6f83b3aabee005"
SRC_URI[video3.sha256sum] = "67f8a8695e7adf54f8d9b3db0e629d3a964c23d0c8011500d1695e618507d16c"
SRC_URI[video4.md5sum] = "f7c78c05ffcfeac4ae1ce06798dfbf05"
SRC_URI[video4.sha256sum] = "a37db801fa8b7edbcfa9c1dfeeee9c1504dd0bd8c67f83b549752add24927666"
SRC_URI[video5.md5sum] = "619825b0713dc39f7689c85750f136a7"
SRC_URI[video5.sha256sum] = "12f283bafefc7f43050b2bee3025245902943131ae496a46c8e79ea4e102fe65"


do_install () {
    install -d ${D}/home/root/videos

    cp -RP ${WORKDIR}/*.mp4  ${D}/home/root/videos

}

INSANE_SKIP_${PN} += "ldflags"
do_patch[noexec] = "1"
do_cofigure[noexec] = "1"
do_compile[noexec] = "1"

PACKAGES = "${PN}"

FILES_${PN} = " /* "
