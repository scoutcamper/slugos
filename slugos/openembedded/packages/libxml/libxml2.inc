DESCRIPTION = "GNOME XML Parser library"
SECTION = "libs"
PRIORITY = "optional"
LICENSE = "MIT"

SRC_URI = "ftp://xmlsoft.org/libxml2/libxml2-${PV}.tar.gz"

inherit autotools pkgconfig binconfig

EXTRA_OECONF = "--without-python --without-debug --without-legacy --without-catalog --without-docbook --with-c14n"

export LDFLAGS += "-ldl"

do_stage() {
	autotools_stage_all
	install -d ${STAGING_DATADIR}/aclocal/
	install -d ${STAGING_BINDIR_CROSS}

 	install -m 0644 libxml.m4 ${STAGING_DATADIR}/aclocal/
	#this is need it by php during its install
	install -m 0755 xml2-config ${STAGING_BINDIR_CROSS}
}

DEBIANNAME_${PN} = "libxml2"

PACKAGES = "${PN}-dbg ${PN}-dev ${PN}-utils ${PN} ${PN}-doc ${PN}-locale"

FILES_${PN}-dev += "${bindir}/*-config"
FILES_${PN}-utils += "${bindir}/* ${libdir}/*.sh"
