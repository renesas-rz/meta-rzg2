require include/rzg2-security-config.inc

SRC_URI_append += " \
    ${@base_conditional("RZG2_SECURITY_FEATURE", "ENABLE", "file://0001-core-arm-plat-rzg-Add-Secure-IP-driver.patch", "",d)} \
    ${@base_conditional("RZG2_SECURITY_FEATURE", "ENABLE", "file://0002-core-arm-plat-rzg-Add-HW-RNG-by-Secure-IP-driver.patch", "",d)} \
    ${@base_conditional("RZG2_SECURITY_FEATURE", "ENABLE", "file://0003-core-arm-plat-rzg-Update-Secure-IP-driver.patch", "",d)} \
    ${@base_conditional("RZG2_SECURITY_FEATURE", "ENABLE", "file://0004-core-arm-plat-rzg-Add-SPI-driver.patch", "",d)} \
    ${@base_conditional("RZG2_SECURITY_FEATURE", "ENABLE", "file://0005-core-arm-plat-rzg-Add-PTA-for-FWU-feature.patch", "",d)} \
"

EXTRA_OEMAKE_append += " \
    ${@base_conditional("RZG2_SECURITY_FEATURE", "ENABLE", "CFG_RZG_SPI_DRV=y CFG_RZG_SEC_IP_DRV=y CFG_RZG_SEC_LIB_DIR=${SYMLINK_NATIVE_SEC_LIB_DIR} CFG_RZG_SEC_IP_RNG=y", "",d)} \
"

DEPENDS += " \
    ${@base_conditional("RZG2_SECURITY_FEATURE", "ENABLE", "secprv-native", "",d)} \
"
