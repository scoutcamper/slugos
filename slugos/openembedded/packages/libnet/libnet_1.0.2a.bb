DESCRIPTION = "Libnet is a collection of routines to help with \
the construction and handling of network packets. It provides a \
portable framework for low-level network packet shaping, \
handling, and injection."
SECTION = "libs/network"
LICENSE = "GPL"
PR = "r1"

# There are major API changes beween libnet v1.0 and libnet v1.1
PROVIDES = "libnet-1.0"

#SRC_URI = "http://www.packetfactory.net/libnet/dist/deprecated/libnet-${PV}.tar.gz \
SRC_URI = "${DEBIAN_MIRROR}/main/libn/libnet0/libnet0_${PV}.orig.tar.gz \
	   file://configure.patch;patch=1 \
	   file://configure.uclibc.patch;patch=1 \
	   file://configure_x86-64-host.patch;patch=1 \
	   "
S = "${WORKDIR}/libnet-${PV}.orig"

inherit autotools

CPPFLAGS_prepend = "-I${S}/libnet/include -DHAVE_PF_PACKET "
EXTRA_OEMAKE = "'LIB_PREFIX=${libdir}/' 'MAN_PREFIX=${mandir}/' \
	        'BIN_PREFIX=${bindir}/' 'INC_PREFIX=${includedir}/'"

#FIXME: (fixing this would remove the need for configure.uclibc.patch above)
do_configure() {
	oe_runconf
}

do_stage() {
	install -m 0755 libnet-config ${STAGING_BINDIR_CROSS}/
	install -m 0644 include/libnet.h ${STAGING_INCDIR}/
	install -d ${STAGING_INCDIR}/libnet
	install -m 0644 include/libnet/libnet-headers.h ${STAGING_INCDIR}/libnet/
	install -m 0644 include/libnet/libnet-functions.h ${STAGING_INCDIR}/libnet/
	install -m 0644 include/libnet/libnet-structures.h ${STAGING_INCDIR}/libnet/
	install -m 0644 include/libnet/libnet-macros.h ${STAGING_INCDIR}/libnet/
	install -m 0644 include/libnet/libnet-asn1.h ${STAGING_INCDIR}/libnet/
	install -m 0644 include/libnet/libnet-ospf.h ${STAGING_INCDIR}/libnet/
	oe_libinstall -a -C lib libnet ${STAGING_LIBDIR}
}
