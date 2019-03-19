ECC="${@'1' if 'ecc' in '${MACHINE_FEATURES}' else '0'}"
USE_ECC="${@'1' if '${ECC}' == '1' else '0'}"
