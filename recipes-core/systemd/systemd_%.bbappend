require include/gles-control.inc

PACKAGECONFIG_remove = "timesyncd"

# Avoid pushing opened device fds into PID1 by logind when restarting
do_install_append () {
    if [ "X${USE_GLES}" = "X1" ]; then
        sed -e 's/FileDescriptorStoreMax=512/FileDescriptorStoreMax=0/' \
            -i ${D}/lib/systemd/system/systemd-logind.service
    fi
}

# Fix build errors with glibc 2.28
#   src/basic/missing_syscall.h:236:19: error: static declaration of 'renameat2' follows non-static declaration
#   src/basic/missing_syscall.h:68:19: error: static declaration of 'memfd_create' follows non-static declaration
#   src/basic/fileio.c:1398:14: error: implicit declaration of function 'memfd_create'; did you mean 'timer_create'? [-Werror=implicit-function-declaration]
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
SRC_URI += "${@'file://0001-Fix-build-errors-with-glibc-2.28.patch' if 'Buster' in '${CIP_MODE}' else ' '}"
