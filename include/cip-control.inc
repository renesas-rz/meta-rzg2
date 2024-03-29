BUSTER_LIMITED_OR_JESSIE="${@'buster-limited' if 'Buster-limited' in '${MACHINE_FEATURES}' else 'jessie'}"
CIP_MODE="${@'buster-full' if 'Buster-full' in '${MACHINE_FEATURES}' else '${BUSTER_LIMITED_OR_JESSIE}'}"

# Using the package versions provided in the security update mirror
# is now necessary to pull updates by Debian LTS
DEBIAN_SECURITY_UPDATE_MIRROR ?= "https://security.debian.org/debian-security/pool/updates"
