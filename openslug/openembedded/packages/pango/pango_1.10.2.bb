LICENSE = "LGPL"
SECTION = "x11/libs"
# Xt needed to keep autoconf's check for X11 happy
DEPENDS = "glib-2.0 fontconfig freetype zlib x11 libxft xt gtk-doc cairo"
DESCRIPTION = "The goal of the Pango project is to provide an \
Open Source framework for the layout and rendering of \
internationalized text."
PR = "r0"

# seems to go wrong with default cflags
FULL_OPTIMIZATION_arm = "-O2"

SRC_URI = "ftp://ftp.gtk.org/pub/gtk/v2.8/pango-${PV}.tar.bz2 \
	   file://no-tests.patch;patch=1"

inherit autotools pkgconfig

EXTRA_OECONF = "--disable-glibtest \
		--enable-explicit-deps=no \
	        --disable-debug"

FILES_${PN} = "/etc ${bindir} ${libdir}/libpango*.so.*"

LIBV = "1.4.0"

do_stage () {
autotools_stage_all
}

postinst_prologue() {
if [ "x$D" != "x" ]; then
  exit 1
fi

}

PACKAGES_DYNAMIC = "pango-module-*"

python populate_packages_prepend () {
	prologue = bb.data.getVar("postinst_prologue", d, 1)

	modules_root = bb.data.expand('${libdir}/pango/${LIBV}/modules', d)

	do_split_packages(d, modules_root, '^pango-(.*)\.so$', 'pango-module-%s', 'Pango module %s', prologue + 'pango-querymodules > /etc/pango/pango.modules')
}
