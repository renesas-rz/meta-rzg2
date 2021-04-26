require include/rzg2-security-config.inc

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SEC_PRV_KEY_ENC = "${SYMLINK_NATIVE_PROV_KEY_DIR}/${FILE_PROVISIONING_KEY_ENC}"

SRC_URI_append = " \
    ${@base_conditional("RZG2_SECURE_BOOT", "ENABLE", "file://0001-Add-the-Provisioning-function-for-SECURE-BOOT.patch", "",d)} \
"

EXTRA_OEMAKE_append += " \
    ${@base_conditional("RZG2_SECURE_BOOT", "ENABLE", "SEC_PRV=ENABLE SEC_PRV_KEY_ENC=${SEC_PRV_KEY_ENC}", "",d)} \
"

DEPENDS += " \
    ${@base_conditional("RZG2_SECURE_BOOT", "ENABLE", "secprv-native", "",d)} \
"
