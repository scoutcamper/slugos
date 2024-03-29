DESCRIPTION = "GNU Radio"
SECTION =  "apps"
PRIORITY = "optional"
LICENSE = "GPL"
DEPENDS = "guile-native fftwf python virtual/libsdl alsa-lib jack boost cppunit sdcc-native swig-native python-numpy"

inherit distutils-base autotools pkgconfig

export BUILD_SYS
export HOST_SYS=${MULTIMACH_TARGET_SYS}

CXXFLAGS_powerpc += "-lstdc++"

EXTRA_OECONF = " \
  ${GR_CPU_SELECTION} \
  --disable-gr-atsc \
  --enable-gr-video-sdl \
  --enable-gr-audio-alsa  \
  --enable-gr-audio-jack \
  --enable-gr-audio-oss \
  --enable-mblock \
  --disable-html-docs \
  --with-pythondir=/usr/lib/${PYTHON_DIR}/site-packages \
  PYTHON_CPPFLAGS=-I${STAGING_INCDIR}/${PYTHON_DIR} \
"

GR_CPU_SELECTION_armv7a = " --with-md-cpu=arm "

do_configure_prepend() {
	if [ -e ${WORKDIR}/acinclude.m4 ] ; then
		cp ${WORKDIR}/acinclude.m4 ${S}
	fi
}

do_configure_append() {
	find ${S} -name Makefile | xargs sed -i s:'-I/usr/include':'-I${STAGING_INCDIR}':g
}

RDEPENDS_${PN} = "python-core python-audio python-threading python-codecs python-lang python-textutils \
                  python-shell python-pickle python-compiler python-pkgutil python-pydoc python-mmap \
                  python-netclient python-unittest python-difflib python-pprint python-numpy \
"

RDEPENDS_gnuradio-usrp = "python-core"

PACKAGES =+ "\
  libpmt libpmt-qa \
  libmblock libmblock-qa \
  libgr-audio-alsa libgr-audio-oss libgr-audio-jack\
  libusrp libusrp-inband libusrp-inband-qa \
  libgromnithread \
  libgnuradio-core libgnuradio-core-qa \
  gnuradio-usrp-dbg gnuradio-usrp gnuradio-examples \
"

FILES_gnuradio-examples = "${datadir}/gnuradio/examples"

FILES_libpmt = "${libdir}/libpmt.so"
FILES_libpmt-qa = "${libdir}/libpmt-qa.so"
FILES_libmblock = "${libdir}/libmblock.so"
FILES_libmblock-qa = "${libdir}/libmblock-qa.so"
FILES_libgr-audio-alsa = "${libdir}/libgr_audio_alsa.so ${sysconfdir}/gnuradio/conf.d/gr-audio-alsa.conf"
FILES_libgr-audio-oss = "${libdir}/libgr_audio_oss.so ${sysconfdir}/gnuradio/conf.d/gr-audio-oss.conf"
FILES_libgr-audio-jack = "${libdir}/libgr_audio_jack.so ${sysconfdir}/gnuradio/conf.d/gr-audio-jack.conf"


FILES_libusrp = "${libdir}/libusrp.so.*"
FILES_libusrp-inband = "${libdir}/libusrp_inband.so.*"
FILES_libusrp-inband-qa = "${libdir}/libusrp_inband-qa.so"
FILES_libgnuradio-core = "${libdir}/libgnuradio-core.so.* ${sysconfdir}/gnuradio/conf.d/*core*"
FILES_libgnuradio-core-qa = "${libdir}/libgnuradio-core-qa.so.*"
FILES_libgromnithread = "${libdir}/libgromnithread.so*"

FILES_${PN} += "${libdir}/python*/site-packages/gnuradio/*"
FILES_${PN}-dbg += "${libdir}/python*/site-packages/gnuradio/.debug \
                   ${libdir}/python*/site-packages/gnuradio/*/.debug \
		   "

FILES_gnuradio-usrp = "${libdir}/python*/site-packages/usrpm/* \
                       ${datadir}/usrp/*/*"
FILES_gnuradio-usrp-dbg = "${libdir}/python*/site-packages/usrpm/.debug"
