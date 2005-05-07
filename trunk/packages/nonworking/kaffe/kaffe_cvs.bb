DESCRIPTION = "Kaffe is a clean room implementation of the Java Virtual Machine"
DEPENDS  = "kaffeh-native  libqpe-opie"
HOMEPAGE = "http://www.kaffe.org"
LICENSE  = "GPL"

SRC_URI  = "cvs://readonly:readonly@cvs.kaffe.org/cvs/kaffe;module=kaffe \
	    file://makefile-fixes.patch;patch=1 "
S = "${WORKDIR}/kaffe"

inherit autotools gettext

CXXFLAGS_append = " -DQPE "
EXTRA_OEMAKE = "MOC=${STAGING_BINDIR}/moc"
EXTRA_OECONF = " --with-qtdir=$QTDIR --with-awt=qt --disable-sound --without-x --enable-pure-java-math --without-classpath-gtk-awt --without-x --without-kaffe-x-awt --with-kaffe-qt-awt "

do_configure_prepend() {
	rm -f m4/libtool.m4
}
