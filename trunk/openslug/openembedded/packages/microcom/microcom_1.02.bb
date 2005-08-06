DESCRIPTION = "microcom is a small minicom-like serial terminal emulator with \
scripting support."
LICENSE = "GPL"
MAINTAINER = "Chris Larson <kergoth@handhelds.org>"

# http://microcom.port5.com/m102.tar.gz is no longer available
SRC_URI = "http://www.oesources.org/source/current/m102.tar.gz \
	   file://make.patch;patch=1"
S = "${WORKDIR}"

do_install () {
	install -d ${D}${bindir}
	install -m 0755 microcom ${D}${bindir}/
}