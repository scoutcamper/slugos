DESCRIPTION = "Midori is a lightweight web browser."
LICENSE = "GPLv2"

DEPENDS = "webkit-gtk libsoup-2.4 libsexy" 

inherit autotools gtk-icon-cache pkgconfig

SRC_URI = "http://software.twotoasts.de/media/midori/midori-${PV}.tar.gz"


