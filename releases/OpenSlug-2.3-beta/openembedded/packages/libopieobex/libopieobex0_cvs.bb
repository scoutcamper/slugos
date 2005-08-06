DESCRIPTION = "IrDA obex support library"
SECTION = "opie/libs"
PRIORITY = "optional"
MAINTAINER = "Team Opie <opie@handhelds.org>"
LICENSE = "LGPL"
DEPENDS = "opie-taskbar"
RDEPENDS = "openobex-apps"
PV = "1.2.0+cvs-${CVSDATE}"
APPNAME = "obex"

SRC_URI = "${HANDHELDS_CVS};module=opie/core/obex \
           ${HANDHELDS_CVS};module=opie/pics"
S = "${WORKDIR}/obex"

inherit opie

do_install() {
        install -d ${D}${palmtopdir}/pics/${APPNAME}/ ${D}${palmtopdir}/plugins/obex/
        install -m 0644 ${WORKDIR}/pics/${APPNAME}/*.png ${D}${palmtopdir}/pics/${APPNAME}/
	oe_libinstall -so libopieobex ${D}${palmtopdir}/plugins/obex/
}

FILES_${PN} = "${palmtopdir}/plugins/obex/ ${palmtopdir}/pics/obex/"