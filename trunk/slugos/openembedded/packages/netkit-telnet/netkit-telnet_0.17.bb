SECTION = "base"
DESCRIPTION = "netkit-telnet includes the telnet daemon and client."
DEPENDS = "ncurses"
LICENSE = "BSD"
PR = "r1"

SRC_URI = "ftp://ftp.uk.linux.org/pub/linux/Networking/netkit/netkit-telnet-${PV}.tar.gz \
           file://netkit-telnet-debian_0.17-36.diff;patch=1 \
           file://cross-compile.patch;patch=1 "

do_configure () {
    ./configure --prefix=${prefix}

    # Inject our ldflags into the config... it is not horning them otherwise
    echo "LDFLAGS=${LDFLAGS}" > MCONFIG
}


do_compile () {
	oe_runmake 'CC=${CC}' 'LD=${LD}' 'LDFLAGS=${LDFLAGS}' SUB=telnet
}

do_install () {
    install -d ${D}${bindir}
    install -m 0755 telnet/telnet ${D}${bindir}/telnet.${PN}
}

pkg_postinst_${PN} () {
#!/bin/sh
    update-alternatives --install ${bindir}/telnet telnet telnet.${PN} 100
}

pkg_prerm_${PN} () {
 #!/bin/sh
   update-alternatives --remove telnet telnet.${PN} 100
}

