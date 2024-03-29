DESCRIPTION = "SWT is an open source widget toolkit for Java designed to provide efficient, portable access to the user-interface facilities"
LICENSE = "CPL MPL LGPL"

inherit java-library

DEPENDS = "fastjar-native cairo gtk+ atk libxtst"

S = "${WORKDIR}/swt-source"

# A number which is used by SWT to mark the shared libraries. It can be found
# in the version.txt file inside the source directory.
SWTVERSION = ${@bb.fatal('SWTVERSION is not defined in this swt-gtk recipe!')}
SWT_API_VERSION = ${@bb.fatal('SWT_API_VERSION is not defined in this swt-gtk recipe!')}

do_unpackpost() {
  if [ ! -d ${S} ]; then
    mkdir ${S}
    cd ${S}
  fi

  unzip -o ../src.zip
  cp ../Makefile .

  sed -i -e "s|RELEASE=OE_SWT_RELEASE|RELEASE=${PV}|" Makefile
  sed -i -e "s|SONAME_VERSION=OE_SWT_API_VERSION|SONAME_VERSION=${SWT_API_VERSION}|" Makefile
}

do_compile() {
  oe_runmake NATIVE_STATS="-I${STAGING_INCDIR}/classpath"
}

addtask unpackpost after do_unpack before do_patch

do_install() {
  oe_jarinstall swt-gtk-${PV}.jar swt-gtk.jar swt.jar

  oe_libinstall -so libswt-atk-gtk-${SWTVERSION} ${D}/${libdir_jni}
  oe_libinstall -so libswt-cairo-gtk-${SWTVERSION} ${D}/${libdir_jni}  
  oe_libinstall -so libswt-pi-gtk-${SWTVERSION} ${D}/${libdir_jni}
  oe_libinstall -so libswt-gtk-${SWTVERSION} ${D}/${libdir_jni}
}

do_stage() {
  oe_jarinstall -s swt-gtk-${PV}.jar swt-gtk.jar swt.jar
}

PACKAGES += "lib${PN}-jni"

FILES_lib${PN}-jni = "${libdir_jni}/lib*.so*"

RDEPENDS_${JPN} = "lib${PN}-jni"
RPROVIDES_${JPN} = "libswt${SWT_API_VERSION}-gtk-java"

