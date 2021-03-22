
require include/rzg2-security-config.inc

SRC_URI_append += " \
    ${@base_conditional("RZG2_SECURE_BOOT", "ENABLE", "file://0001-core-arm-plat-rzg-Add-Secure-IP-driver.patch", "",d)} \
    ${@base_conditional("RZG2_SECURE_BOOT", "ENABLE", "file://0002-core-arm-plat-rzg-Add-HW-RNG-by-Secure-IP-driver.patch", "",d)} \
"

EXTRA_OEMAKE_append += " \
    ${@base_conditional("RZG2_SECURE_BOOT", "ENABLE", " CFG_RZG_SEC_IP_DRV=y CFG_RZG_SEC_LIB_DIR=${SYMLINK_NATIVE_SEC_LIB_DIR} CFG_RZG_SEC_IP_RNG=y", "",d)} \
"

DEPENDS += " \
    ${@base_conditional("RZG2_SECURE_BOOT", "ENABLE", "secprv-native", "",d)} \
"
