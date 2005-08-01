SECTION = "libs"
DESCRIPTION = "libdaemon is a lightweight C library which eases the writing of UNIX daemons."
LICENSE ="GPL"
SRC_URI = "http://0pointer.de/lennart/projects/libdaemon/libdaemon-${PV}.tar.gz"

inherit autotools 

EXTRA_OECONF = "--disable-lynx"

do_stage () {
	oe_libinstall -a -so -C src libdaemon ${STAGING_LIBDIR}
	install -d ${STAGING_INCDIR}/libdaemon
	for i in dlog.h dfork.h dsignal.h dnonblock.h dpid.h; do
		install -m 0644 ${S}/src/$i ${STAGING_INCDIR}/libdaemon/
	done
}