require include/docker-control.inc

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append = "\
  ${@base_conditional("USE_DOCKER", "1", " file://0001-Modify-tool-install-script-to-avoid-build-error.patch ", "", d)} \
"
