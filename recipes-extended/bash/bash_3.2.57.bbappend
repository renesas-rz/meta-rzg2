# bashbug can cause conflict in lib32 and lib64, separate it from bash pacakge
PACKAGES += "${PN}-bashbug"
FILES_${PN} = "${bindir}/bash ${base_bindir}/bash.bash"
FILES_${PN}-bashbug = "${bindir}/bashbug"

