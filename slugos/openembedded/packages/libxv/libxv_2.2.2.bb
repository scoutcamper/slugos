LICENSE = "GPL"
SECTION = "x11/libs"
DEPENDS = "x11 xext"
DESCRIPTION = "X Video extension library."

SRC_URI = "${XLIBS_MIRROR}/libXv-${PV}.tar.bz2"
S = "${WORKDIR}/libXv-${PV}"

inherit autotools pkgconfig 

do_stage() {
	autotools_stage_all
}
