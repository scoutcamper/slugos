DESCRIPTION = "Google Chrome browser"
LICENSE = "BSD"

DEPENDS = "perl-native python-native flex-native gperf-native"

PV = "0.0+svnr${SRCREV}"

SRCREV = "1665"
SRC_URI = "svn://src.chromium.org/svn/trunk/;module=src;proto=http"

S = "${WORKDIR}/src/chrome"

do_compile() {
	cd ${S}
	../third_party/scons/scons.py Hammer
}	
