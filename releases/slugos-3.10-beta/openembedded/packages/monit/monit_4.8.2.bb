LICENSE = "GPL"
DEPENDS = "openssl"

SRC_URI = "http://www.tildeslash.com/monit/dist/monit-${PV}.tar.gz"

inherit autotools

EXTRA_OECONF = "--with-ssl-lib-dir=${STAGING_LIBDIR} --with-ssl-incl-dir=${STAGING_INCDIR}" 