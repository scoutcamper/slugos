PV = "0.0+cvs${SRCDATE}"
SECTION = "x11/libs"
PRIORITY = "optional"
MAINTAINER = "Phil Blundell <pb@handhelds.org>"
DEPENDS = "x11 libsm"
DESCRIPTION = "X Toolkit Intrinsics"
LICENSE =  "X-MIT"
PR = "r1"
SRC_URI = "cvs://anoncvs:anoncvs@pdx.freedesktop.org/cvs/xlibs;module=Xt"
S = "${WORKDIR}/Xt"

inherit autotools pkgconfig 


do_compile() {
	(
		unset CC LD CXX CCLD
		oe_runmake -C util 'CC=${BUILD_CC}' 'LD=${BUILD_LD}' 'CFLAGS=' 'LDFLAGS=' 'CXXFLAGS=' 'CPPFLAGS=' makestrs
	)
	oe_runmake
}


do_stage () {
	autotools_stage_all
}
