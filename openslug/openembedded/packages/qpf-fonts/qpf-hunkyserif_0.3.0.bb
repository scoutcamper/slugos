DESCRIPTION = "Hunky Serif font - QPF Edition"
SECTION = "opie/fonts"
PRIORITY = "optional"
MAINTAINER = "Marcin Juszkiewicz <openembedded@hrw.one.pl>"
LICENSE = "Bitstream Vera"
HOMEPAGE = "http://www.yoper.com/ariszlo/hunky.html http://sourceforge.net/projects/hunkyfonts"
PACKAGE_ARCH = "all"
PR = "r3"

SRC_URI = "http://www.hrw.one.pl/_pliki/oe/files/${PN}-${PV}.tar.bz2"

S = "${WORKDIR}/${PN}"

do_install () { 
        install -d ${D}${palmqtdir}/lib/fonts/ 
        for i in *.qpf; do 
                install -m 644 $i ${D}${palmqtdir}/lib/fonts/${i} 
        done 
} 

inherit qpf
