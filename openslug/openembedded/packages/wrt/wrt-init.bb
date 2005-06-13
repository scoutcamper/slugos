DESCRIPTION = "wrt init scripts"
SECTION = "base"
LICENSE = "GPL"

SRC_URI = "file://mount file://wrtboot"

do_install() {
	install -d ${D}${sysconfdir}/rc.d \
		   ${D}${sysconfdir}/init.d
	
	install -m 0755 ${WORKDIR}/mount ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/wrtboot ${D}${sysconfdir}/init.d
	
	ln -s ../init.d/mount ${D}${sysconfdir}/rc.d/S05mount
	# this script comes from base-files
	ln -s ../init.d/populate-var.sh ${D}${sysconfdir}/rc.d/S06populate-var.sh
	ln -s ../init.d/wrtboot ${D}${sysconfdir}/rc.d/S10wrtboot
}
