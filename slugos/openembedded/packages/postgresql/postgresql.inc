DESCRIPTION = "PostgreSQL is a powerful, open source relational database system."
HOMEPAGE = "http://www.postgresql.com"
LICENSE = "BSD"
DEPENDS = "zlib readline"

ARM_INSTRUCTION_SET = "arm"

#WARNING: this recipe assumes you have the timezone compiler present in /usr/sbin/zic

SRC_URI = "ftp://ftp-archives.postgresql.org/pub/source/v${PV}/${P}.tar.bz2"

LEAD_SONAME = "libpq.so"

inherit autotools pkgconfig

do_compile_append() {
         cp /usr/sbin/zic ${S}/src/timezone/
}

do_stage() {
         autotools_stage_includes
         
         oe_libinstall -a -so -C src/interfaces/libpq libpq ${STAGING_LIBDIR}
         oe_libinstall -a -so -C src/interfaces/ecpg/ecpglib libecpg ${STAGING_LIBDIR}
         oe_libinstall -a -so -C src/interfaces/ecpg/compatlib libecpg_compat ${STAGING_LIBDIR}
         oe_libinstall -a -so -C src/interfaces/ecpg/pgtypeslib libpgtypes ${STAGING_LIBDIR}
}

PACKAGES =+ "${PN}-client ${PN}-server-dev ${PN}-timezone \
             libecpg-compat-dbg libecpg-compat \
             libecpg-dbg libecpg libecpg-dev \
             libpq-dbg libpq libpq-dev \ 
             libpgtypes-dbg libpgtypes "

FILES_${PN}-client = "${bindir}/clusterdb \
                ${bindir}/createdb \
                ${bindir}/createlang \
                ${bindir}/createuser \
                ${bindir}/dropdb \
                ${bindir}/droplang \
                ${bindir}/dropuser \
                ${bindir}/pg_dump \
                ${bindir}/pg_dumpall \
                ${bindir}/pg_restore \
                ${bindir}/psql \
                ${bindir}/reindexdb \
                ${bindir}/vacuumdb \
                ${bindir}/vacuumlo \
                ${datadir}/${PN}/psqlrc.sample"

FILES_${PN}-doc += "${prefix}/doc/"
FILES_${PN}-timezone = "${datadir}/${PN}/timezone"
FILES_${PN}-server-dev = "${includedir}/${PN}/server"

FILES_libecpg = "${libdir}/libecpg*.so.*"
FILES_libecpg-dbg = "${libdir}/.debug/libecpg*"
FILES_libecpg-dev = "${libdir}/libecpg*.a ${libdir}/libecpg*.so \
                     ${libdir}/libpgtypes*.a ${libdir}/libpgtypes*.so \
                     ${includedir}/ecpg*.h ${includedir}/${PN}/ecpg*.h \
                     ${includedir}/pgtypes*.h ${includedir}/${PN}/informix \
                     ${includedir}/sql3types.h ${includedir}/sqlca.h"

FILES_libpq = "${libdir}/libpq*.so.*"
FILES_libpq-dbg = "${libdir}/.debug/libpq*"
FILES_libpq-dev = "${libdir}/libpq*.a ${libdir}/libpq*.so ${libdir}/libpgport.a \
                   ${includedir}"

FILES_libecpg-compat = "${libdir}/libecpg_compat*.so.*"
FILES_libecpg-compat-dbg = "${libdir}/.debug/libecpg_compat*"
FILES_libpgtypes = "${libdir}/libpgtypes*.so.*"
FILES_libpgtypes-dbg = "${libdir}/.debug/libpgtypes*"
