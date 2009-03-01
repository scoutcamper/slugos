DESCRIPTION = "Python Gstreamer bindings"
SECTION = "devel/python"
LICENSE = "LGPL"
DEPENDS = "gstreamer gst-plugins-base python-pygobject"
PR = "ml3"

SRC_URI = "\
  http://gstreamer.freedesktop.org/src/gst-python/gst-python-${PV}.tar.bz2 \
  file://python-path.patch;patch=1 \
  file://import-gobject-instead-of-pygtk.patch;patch=1 \
"
S = "${WORKDIR}/gst-python-${PV}"

inherit autotools distutils-base pkgconfig

EXTRA_OECONF += "--with-python-includes=${STAGING_INCDIR}/../"

do_configure_prepend() {
    install -d ${S}/m4
    install -m 0644 ${S}/common/m4/*.m4 ${S}/m4/
}

do_stage() {
	autotools_stage_all
}

PACKAGES =+ "${PN}-examples"

FILES_${PN} += "${datadir}/gst-python"
FILES_${PN}-dev += "\
  ${datadir}/gst-python/0.10/defs \
  ${libdir}/${PYTHON_DIR}/site-packages/gst-0.10/gst/*.la \
"
FILES_${PN}-dbg += "${libdir}/${PYTHON_DIR}/site-packages/gst-0.10/gst/.debug/"
FILES_${PN}-examples = "${datadir}/gst-python/0.10/examples"
