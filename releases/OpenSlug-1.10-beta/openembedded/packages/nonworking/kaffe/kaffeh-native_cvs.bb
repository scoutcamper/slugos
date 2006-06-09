DESCRIPTION = "Kaffe is a clean room implementation of the Java Virtual Machine"
DEPENDS  = "patcher-native"
HOMEPAGE = "http://www.kaffe.org"
LICENSE  = "GPL"

SRC_URI  = "cvs://readonly:readonly@cvs.kaffe.org/cvs/kaffe;module=kaffe \
	    file://makefile-fixes.patch;patch=1 "
S = "${WORKDIR}/kaffe"

inherit autotools native

EXTRA_OECONF = " --disable-nls --disable-native-awt --disable-sound --without-x --without-kaffe-x-awt --disable-debug --enable-pure-java-math --disable-gcj "


do_configure_prepend() {
	rm -f m4/libtool.m4
}

do_stage() {
	install kaffe/kaffeh/kaffeh ${STAGING_BINDIR}/
}
