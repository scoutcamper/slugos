DESCRIPTION = "AbiWord is free word processing program similar to Microsoft(r) Word"
HOMEPAGE = "http://www.abiword.org""
SECTION = "x11/office"
LICENSE = "GPLv2"
DEPENDS     = "perl-native wv libgsf libgnomeprint libgnomeprintui libglade libfribidi enchant jpeg libpng libxml2"
RDEPENDS    = "enchant glibc-gconv-ibm850 glibc-gconv-cp1252 \
               glibc-gconv-iso8859-15 glibc-gconv-iso8859-1"

SRC_URI = "http://www.abiword.org/downloads/abiword/${PV}/source/abiword-${PV}.tar.gz"

#want 2.x from 2.x.y for the installation directory
SHRT_VER = "${@bb.data.getVar('PV',d,1).split('.')[0]}.${@bb.data.getVar('PV',d,1).split('.')[1]}"

FILES_${PN} += " \
		${datadir}/icons/* \
		${datadir}/abiword-${SHRT_VER}/glade \
		${datadir}/abiword-${SHRT_VER}/scripts \
		${datadir}/abiword-${SHRT_VER}/system.profile-en \
		${datadir}/abiword-${SHRT_VER}/system.profile-en_GB \
#		${datadir}/abiword-${SHRT_VER}/templates/A4.awt \
#		${datadir}/abiword-${SHRT_VER}/templates/US-Letter.awt \
		${datadir}/abiword-${SHRT_VER}/templates/normal.awt \
		${datadir}/abiword-${SHRT_VER}/templates/normal.awt-en_GB \
		${datadir}/abiword-${SHRT_VER}/templates/Employee-Directory.awt \
		${datadir}/abiword-${SHRT_VER}/templates/Business-Report.awt \
		${datadir}/abiword-${SHRT_VER}/templates/Fax-Coversheet.awt \
		${datadir}/abiword-${SHRT_VER}/templates/Resume.awt \
		${datadir}/abiword-${SHRT_VER}/templates/Two-Columns.awt \
		${datadir}/abiword-${SHRT_VER}/templates/Memo.awt \
		${datadir}/abiword-${SHRT_VER}/templates/Press-Release.awt "

inherit autotools pkgconfig

PARALLEL_MAKE = ""

EXTRA_OECONF = " --disable-pspell \
                 --enable-enchant \
		 --with-sys-wv"

# AbiWord configure.ac does not play nicely with autoreconf
# so use the autogen.sh script that comes with AbiWord
#
do_configure() {
        cd ${S}
        export NOCONFIGURE="no"; ./autogen.sh
        oe_runconf
}


do_install_append() {
        install -d ${D}${datadir}/pixmaps/
        mv ${D}${datadir}/icons/* ${D}${datadir}/pixmaps/
}

PACKAGES += " abiword-clipart abiword-icons abiword-strings abiword-systemprofiles abiword-templates "

FILES_abiword-clipart 		+= "${datadir}/abiword-${SHRT_VER}/clipart"
FILES_abiword-icons 		+= "${datadir}/abiword-${SHRT_VER}/icons"
FILES_abiword-strings 		+= "${datadir}/abiword-${SHRT_VER}/AbiWord/strings"
FILES_abiword-systemprofiles 	+= "${datadir}/abiword-${SHRT_VER}/AbiWord/system.profile*"
FILES_abiword-templates 	+= "${datadir}/abiword-${SHRT_VER}/templates"
