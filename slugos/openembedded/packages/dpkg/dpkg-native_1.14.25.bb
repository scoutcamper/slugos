require dpkg.inc
DEPENDS += "bzip2-native zlib-native virtual/update-alternatives-native"
SRC_URI += "file://noman.patch;patch=1"

FILESPATH += "dpkg-${PV}"

inherit native

EXTRA_OECONF = "--without-static-progs \
		--without-dselect \
		--with-start-stop-daemon \
		--with-zlib \
		--with-bz2lib \
                --without-selinux \
		--without-sgml-doc"
