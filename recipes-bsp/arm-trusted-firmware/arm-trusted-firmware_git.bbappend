require include/provisioning.inc

SEC_MODULE_LIBDIR = "${HOME}/private/secure/lib/"

SRC_URI_append += " \
	${@base_conditional("SECURE_BOOT", "1", "file://0001-rzg-add-support-SECURE-BOOT-for-RZ-G2-Platform.patch", "",d)} \
"

ATFW_OPT_append += " \
	${@base_conditional("SECURE_BOOT", "1", "SPD=\"opteed\"", "",d)} \
	${@base_conditional("SECURE_BOOT", "1", "RZG2_SECURE_BOOT=1 SEC_MODULE_LIBDIR=${SEC_MODULE_LIBDIR}", "",d)} \
"

do_compile_append() {
	if [ 1 -eq ${SECURE_BOOT} ] ; then
		oe_runmake sec_module PLAT=${PLATFORM} ${ATFW_OPT}
	fi
}

do_deploy_append() {
	if [ 1 -eq ${SECURE_BOOT} ] ; then
		install -m 0644 ${S}/build/${PLATFORM}/release/sec_module.srec ${DEPLOYDIR}/sec_module_${MACHINE}.srec
		do_deploy_provisioning 0x44000000 "${DEPLOYDIR}/bl31-${MACHINE}.srec"

		ENC_KEY_BIN="${ENCRYPTED_KEYRING}"
		objcopy -I binary -O srec --adjust-vma=0x440FE000 --srec-forceS3 "${ENC_KEY_BIN}" "${DEPLOYDIR}/${ENC_KEY_BIN##*/}.srec"
	fi
}
