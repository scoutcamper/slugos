LICENSE = 	"LGPL"
MAINTAINER = "Koen Kooi <koen@handhelds.org>"

DEPENDS =	"dbus hildon-lgpl hildon-fm outo gtk+-2.6.4-1.osso7"
SRC_URI =	"http://repository.maemo.org/pool/maemo/ossw/source/h/hildon-libs/hildon-libs_${PV}.tar.gz \
			file://hildon-libs-no-werror.patch;patch=1"

S =	"${WORKDIR}/hildon-libs-0.9.6"

inherit pkgconfig autotools
EXTRA_OECONF =	"--disable-gtk-doc"


do_stage() {
	autotools_stage_includes()
}

