# Fix the issue missed Perl modules in navtive SDK sysroot
FILES_${PN}_append_class-nativesdk = " ${libdir}/perl/${PV}/*"

# Fix build error with glibc 2.28 (libm and libnsl not available anymore)
do_configure_prepend() {
	sed -i -e "s,\(d_libm_lib_version=\)'define',\1'undef',g" -e "s,-lnsl,,g" ${WORKDIR}/config.sh
}
