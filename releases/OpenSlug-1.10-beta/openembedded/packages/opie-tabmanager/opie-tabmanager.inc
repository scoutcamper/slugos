DESCRIPTION = "Tab Manager"
SECTION = "opie/settings"
PRIORITY = "optional"
MAINTAINER = "Team Opie <opie@handhelds.org>"
LICENSE = "GPL"
APPNAME = "tabmanager"


S = "${WORKDIR}/${APPNAME}"

inherit opie

# FILES plugins/application/libtabmanager.so* bin/tabmanager apps/Settings/tabmanager.desktop pics/tabmanager/tabmanager.png
do_install() {
        install -d ${D}${palmtopdir}/pics/${APPNAME}/
        install -m 0644 ${WORKDIR}/pics/${APPNAME}/*.png ${D}${palmtopdir}/pics/${APPNAME}/
}

