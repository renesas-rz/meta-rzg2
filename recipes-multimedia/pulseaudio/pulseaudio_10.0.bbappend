#Fix build error with glibc due to memfd_create (added from version 2.27)
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
SRC_URI += "${@'file://0001-memfd-wrappers-only-define-memfd_create-if-not-alrea.patch' if '${GLIBCVERSION}' >= '2.27' else ' '}"
