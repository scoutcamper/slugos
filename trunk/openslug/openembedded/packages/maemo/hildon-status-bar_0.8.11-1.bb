PR         = "r0"
LICENSE    = "GPL"
MAINTAINER = "Florian Boor <florian@kernelconcepts.de>"

DEPENDS = "hildon-lgpl libosso hildon-libs"

SRC_URI = "http://repository.maemo.org/pool/maemo/ossw/source/h/${PN}/${PN}_${PV}.tar.gz"

S = "${WORKDIR}/hildon-status-bar-0.8.11"

inherit autotools pkgconfig

