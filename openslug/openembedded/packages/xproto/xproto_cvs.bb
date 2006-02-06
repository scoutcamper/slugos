PV = "0.0+cvs${SRCDATE}"
LICENSE= "MIT"
SECTION = "x11/libs"
MAINTAINER = "Greg Gilbert <greg@treke.net>"
DESCRIPTION = "X protocol and ancillary headers."

SRC_URI = "cvs://anoncvs:anoncvs@pdx.freedesktop.org/cvs/xlibs;module=Xproto"
S = "${WORKDIR}/Xproto"

inherit autotools pkgconfig


do_stage() {
	autotools_stage_all
}
