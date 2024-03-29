DESCRIPTION = "PowerTOP, a tool that helps you find what software is using the most power."
HOMEPAGE = "http://www.linuxpowertop.org/"
LICENSE = "GPLv2"
DEPENDS = "virtual/libintl ncurses"

SRC_URI = "http://www.lesswatts.org/projects/powertop/download/powertop-${PV}.tar.gz"

SRC_URI_append_beagleboard = " file://omap.patch;patch=1;pnum=0"

CFLAGS += "${LDFLAGS}"
CFLAGS_beagleboard += "-DOMAP3"

do_configure() {
    # We do not build ncurses with wide char support
    sed -i -e "s/lncursesw/lncurses/" ${S}/Makefile
}

do_install() {
    oe_runmake install DESTDIR=${D}
}
