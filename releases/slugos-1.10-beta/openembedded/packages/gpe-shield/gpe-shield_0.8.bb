PR = "r0"
inherit gpe pkgconfig
LICENSE = "GPL"
DEPENDS = "libgpewidget iptables virtual/kernel"
RDEPENDS = "iptables kernel-module-ipt-state"
SECTION = "gpe"
MAINTAINER = "Florian Boor <florian.boor@kernelconcepts.de>"

DESCRIPTION = "GPE network security tool"

