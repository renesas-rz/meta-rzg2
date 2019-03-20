FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append = " \
	file://0001-example-test-multicast2-revise-pipeline-to-read-from.patch \
"

do_install_append() {
	install -d ${D}/usr/bin
	cp ${WORKDIR}/build/examples/.libs/* ${D}/usr/bin
}
