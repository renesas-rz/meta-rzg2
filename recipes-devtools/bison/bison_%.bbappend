# Call create_wrapper to relocation the datadir for nativesdk-bison.
# But if file bison.real exists, current bison binary was already relocated.
do_install_append_class-nativesdk() {
	if [ ! -f ${D}/${bindir}/bison.real ]
	then
		create_wrapper ${D}/${bindir}/bison \
			BISON_PKGDATADIR=${datadir}/bison
	fi
}
