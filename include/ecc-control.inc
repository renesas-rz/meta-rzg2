ECC="${@'1' if 'ecc' in '${MACHINE_FEATURES}' else '0'}"
USE_ECC="${@'1' if '${ECC}' == '1' else '0'}"

ECC_FULL="${@'1' if ('${USE_ECC}' == '1') and ('${ECC_MODE}' == 'Full') else '0'}"
