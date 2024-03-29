SECTION = "console/network"
DESCRIPTION = "MobileMesh mobile ad-hoc routing protocol"
HOMEPAGE = "http://www.mitre.org/work/tech_transfer/mobilemesh/"
LICENSE = "GPL"
DEPENDS = "openssl"
SRC_URI = "http://meshcube.org/download/mobilemesh_1.2.tgz"
PR = "r1"

S = "${WORKDIR}/mobilemesh"

CXXFLAGS += "-I ."
LDFLAGS += "-lcrypto -lssl"

do_compile() {
	oe_runmake depends
	oe_runmake
}

do_install() {
	install -d ${D}${sbindir}
	install -d ${D}${sysconfdir}/mobilemesh
	install -d ${D}${sysconfdir}/init.d
	install -d ${D}${docdir}
	install -d ${D}${mandir}/man8
	oe_runmake PREFIX=${TARGET_PREFIX} BINDIR=${D}${sbindir} ETCDIR=${D}${sysconfdir}/mobilemesh install
	install ${S}/doc/InternetDrafts/draft* ${D}${docdir}
	#install ${S}/doc/man/mm*.ps ${D}${docdir}
	install ${S}/doc/man/mm*.pdf ${D}${docdir}
	install ${S}/doc/man/mm*.8 ${D}${mandir}/man8
	install ${S}/doc/FAQ ${D}${docdir}
	install ${S}/doc/INSTALL ${D}${docdir}
	install ${S}/doc/LICENSE ${D}${docdir}
	install ${S}/doc/README ${D}${docdir}
	install ${S}/mobilemesh.init ${D}${sysconfdir}/init.d/mobilemesh
}

CONFFILES_${PN}_nylon = "${sysconfdir}/mobilemesh/mmrp.conf ${sysconfdir}/mobilemesh/mmdiscover.conf"
