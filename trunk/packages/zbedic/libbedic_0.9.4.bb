DESCRIPTION = "A dictionary application for Qt/E based Palmtop Environments"
SECTION = "opie/applications"
PRIORITY = "optional"
MAINTAINER = "Michael 'Mickey' Lauer <mickey@Vanille.de>"
LICENSE = "GPL"
APPTYPE = "binary"
APPDESKTOP = "${WORKDIR}/zbedic/misc"

SRC_URI = "${SOURCEFORGE_MIRROR}/bedic/libbedic_0.9.4-0.tgz "

S = "${WORKDIR}"

inherit palmtop

do_configure() {
	qmake -project && qmake -makefile -t lib -spec ${QMAKESPEC} CONFIG=console CONFIG+=staticlib -after \
        INCLUDEPATH+=../include TARGET=bedic DESTDIR=${STAGING_LIBDIR} HEADERS+=src/file.h SOURCES+=src/file.cpp
}

do_stage() {

    install -d ${STAGING_INCDIR}/libbedic/
    install -m 0644 ${S}/include/*.h ${STAGING_INCDIR}/libbedic/
}
