DESCRIPTION = "Ratoc cfu1 CF USB 1.1 host card driver"
MAINTAINER = "Simon Pickering <S.G.Pickering@bath.ac.uk>"
SECTION = "kernel/modules"
LICENSE = "GPL"
PRIORITY = "optional"
RDEPENDS = "kernel-module-usbcore kernel-module-sl811-hcd"
PR = "r1"

SRC_URI = "file://rex-cfu1.conf \
           file://Makefile \
           file://sl811_cs.c \
           file://sl811_hcd.c \
           file://sl811.h \
           file://hcd.h \
           file://hub.h"

S = "${WORKDIR}"

inherit module

#EXTRA_OEMAKE = "CFLAGS=-DDEBUG -C ${STAGING_KERNEL_DIR} SUBDIRS=${WORKDIR}"
EXTRA_OEMAKE = "-C ${STAGING_KERNEL_DIR} SUBDIRS=${WORKDIR}"

do_compile() {
	unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
	oe_runmake modules
}
		

do_install() {
	install -d ${D}/lib/modules/${KERNEL_VERSION}/pcmcia/
	install -m 0644 sl811_cs.ko ${D}/lib/modules/${KERNEL_VERSION}/pcmcia/

	install -d ${D}/${sysconfdir}/pcmcia/
	install -m 0644 ${WORKDIR}/rex-cfu1.conf ${D}/${sysconfdir}/pcmcia/
}

