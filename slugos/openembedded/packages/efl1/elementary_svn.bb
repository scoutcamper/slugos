DESCRIPTION = "EFL based widget set for mobile devices"
LICENSE = "LGPL"
DEPENDS = "evas ecore edje eet edbus"
PV = "0.0.0+svnr${SRCREV}"
PR = "r3"

inherit efl

SRC_URI = "svn://svn.enlightenment.org/svn/e/trunk/TMP/st;module=elementary;proto=http"
S = "${WORKDIR}/elementary"
PACKAGES =+ "${PN}-tests"

RDEPENDS = "elementary-themes"
RRECOMMENDS = "elementary-tests"

do_compile_append() {
        sed -i -e s:${STAGING_DIR_TARGET}::g \
               -e s:/${TARGET_SYS}::g \
                  elementary.pc
}

FILES_${PN}-themes = "\
  ${datadir}/elementary/themes \
"

FILES_${PN}-tests = "\
  ${bindir}/elementary* \
  ${datadir}/elementary/images \
  ${datadir}/elementary/objects \
  ${datadir}/applications/* \
  ${datadir}/icons/* \
"
