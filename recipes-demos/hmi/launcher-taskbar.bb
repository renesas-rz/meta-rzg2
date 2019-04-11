LICENSE = "CLOSED"
DESCRIPTION = "launcher taskbar"

SRC_URI = " \	
 file://hmi-icons/* \
 file://hmi-scripts/* \
"

INSANE_SKIP_${PN} = "already-stripped"

do_install() {
    install -d ${D}/home/root/launcher-taskbar/hmi-icons
    install -d ${D}/home/root/launcher-taskbar/hmi-scripts
    install -d ${D}/home/root/videos

    cp -Rf ${S}/../hmi-icons/* ${D}/home/root/launcher-taskbar/hmi-icons/
    cp -Rf ${S}/../hmi-scripts/* ${D}/home/root/launcher-taskbar/hmi-scripts/

}

INSANE_SKIP_${PN} += "file-rdeps"

FILES_${PN} = "/home/root/launcher-taskbar/hmi-icons \
               /home/root/launcher-taskbar/hmi-scripts \
               /home/root \
               /home/root/videos \
"
FILES_${PN}-dbg += " \
 /home/root/launcher-taskbar/hmi-scripts/.debug/* \
 /home/root/launcher-taskbar/hmi-scripts/*/.debug/* \
"
