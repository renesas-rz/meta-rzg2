LIC_FILES_CHKSUM = "file://docs/license.rst;md5=713afe122abbe07f067f939ca3c480c5"

require trusted-firmware-a.inc

URL = "git://git@github.com/renesas-rz/rzg_trusted-firmware-a.git"
BRANCH = "v2.5/rzg2l"
SRCREV = "d4c55f30cca4c7f6682f70530eda52e28555230f"

SRC_URI += "${URL};protocol=ssh;branch=${BRANCH}"

PV = "2.5-rzg+git${SRCPV}"
PR = "r1"
