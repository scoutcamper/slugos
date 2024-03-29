DESCRIPTION = "Bootchart Lite for Embedded Systems."
AUTHOR = "Fred Chien"
LICENSE = "GPLv2"
SECTION = "console/utils"
HOMEPAGE = "http://code.google.com/p/bootchart-lite/"
PV = "0.1+svnr${SRCREV}"
PR = "r0"

SRC_URI = "svn://bootchart-lite.googlecode.com/svn/;module=trunk;proto=http"
S = "${WORKDIR}/trunk"

inherit autotools

pkg_postinst_${PN} () {
	install -d /etc/bootchart-lite
}

pkg_postrm_${PN} () {
	rm -rf /etc/bootchart-lite
}
