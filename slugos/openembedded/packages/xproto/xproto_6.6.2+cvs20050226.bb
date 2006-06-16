LICENSE= "MIT"
SECTION = "x11/libs"
MAINTAINER = "Greg Gilbert <greg@treke.net>"
DESCRIPTION = "X protocol and ancillary headers."

SRC_URI = "cvs://anoncvs:anoncvs@pdx.freedesktop.org/cvs/xlibs;module=Xproto;date=20050226"
S = "${WORKDIR}/Xproto"

inherit autotools pkgconfig

do_stage() {
	autotools_stage_all
}
