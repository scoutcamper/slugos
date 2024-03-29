# Copyright (C) 2008 Instituto Nokia de Tecnologia
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Mamona-IM Enlightenment Applet"
HOMEPAGE = "http://dev.openbossa.org/trac/mamona/wiki"
LICENSE = "MIT BSD"
DEPENDS = "mamona-input-methods e-wm"
PR = "r0"

PV = "0.1+git"

inherit autotools pkgconfig

SRC_URI = "git://dev.openbossa.org/mamona/projects/mamonaim_e_applet.git;protocol=http"

S = "${WORKDIR}/git"

# E Applet
FILES_${PN} = "\
            ${libdir}/enlightenment/modules/mamonaim/module.desktop \
            ${libdir}/enlightenment/modules/mamonaim/mamonaim.edj \
            ${libdir}/enlightenment/modules/mamonaim/e-module-mamonaim.edj \
            ${libdir}/enlightenment/modules/mamonaim/*.png \
            ${libdir}/enlightenment/modules/mamonaim/*/module.so \
        "
FILES_${PN}-dev = "\
            ${libdir}/enlightenment/modules/mamonaim/*/module.la \
            ${libdir}/enlightenment/modules/mamonaim/*/module.a \
        "
FILES_${PN}-dbg = "\
            ${libdir}/enlightenment/modules/mamonaim/*/.debug \
        "

do_configure_prepend() {
    ./autogen.sh
}
