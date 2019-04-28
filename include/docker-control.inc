DOCKER="${@'1' if 'docker' in '${MACHINE_FEATURES}' else '0'}"
USE_DOCKER="${@'1' if '${DOCKER}' == '1' else '0'}"
