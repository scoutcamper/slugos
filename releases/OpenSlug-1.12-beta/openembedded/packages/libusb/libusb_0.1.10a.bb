DESCRIPTION = "libusb is a library to provide userspace \
access to USB devices."
SECTION = "libs"
LICENSE = "LGPL"
PR = "r1"

SRC_URI = "${SOURCEFORGE_MIRROR}/libusb/libusb-${PV}.tar.gz" 

inherit autotools pkgconfig

EXTRA_OECONF = "--disable-build-docs"

do_stage() {
	oe_libinstall -a -so libusb ${STAGING_LIBDIR}

        install -d ${STAGING_BINDIR}
	install -m 755 ${S}/libusb-config ${STAGING_BINDIR}
	perl -pi -e 's:\-L${libdir} :-L${STAGING_LIBDIR} :' ${STAGING_BINDIR}/libusb-config

        install -d ${STAGING_INCDIR}/
        for X in usb.h
        do
                install -m 0644 ${S}/$X ${STAGING_INCDIR}/$X
        done
}