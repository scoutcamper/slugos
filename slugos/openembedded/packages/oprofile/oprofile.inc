SECTION = "devel"
DESCRIPTION = "OProfile is a system-wide profiler for Linux systems, capable \
of profiling all running code at low overhead."
LICENSE = "GPL"
DEPENDS = "popt binutils"

S = "${WORKDIR}/oprofile-${PV}"

inherit autotools

# NOTE: this disables the build of the kernel modules.
# Should add the oprofile kernel modules, for those with 2.4
# kernels, as a seperate .oe file.
EXTRA_OECONF = "--with-kernel-support \
		--without-x \
		--disable-werror "

do_configure () {
	cp ${WORKDIR}/acinclude.m4 ${S}/
	autotools_do_configure
}
# Available config options
#  --enable-abi                 enable abi portability code (default is disabled)
#  --enable-pch                 enable precompiled header (default is disabled)
#  --enable-gcov                enable option for gcov coverage testing (default is disabled)
#  --disable-werror            disable -Werror flag (default is enabled for non-release)
#  --disable-optimization      disable optimization flags (default is enabled)
#  --with-kernel-support        Use 2.6 kernel (no kernel source tree required)
#  --with-linux=dir             Path to Linux source tree
#  --with-module-dir=dir        Path to module installation directory
#  --with-extra-includes=DIR    add extra include paths
#  --with-extra-libs=DIR        add extra library paths
#  --with-x                use the X Window System
#  --with-qt-dir           where the root of Qt is installed
#  --with-qt-includes      where the Qt includes are.
#  --with-qt-libraries     where the Qt library is installed.

OPROFILE_ARCH = "${TARGET_ARCH}"
OPROFILE_ARCH_i586 = "i386"
# note this excludes the ppc64 directory 
# but we do not support ppc64 targets yet.
OPROFILE_ARCH_powerpc = "ppc"

FILES_${PN} = "${bindir} ${datadir}/oprofile/rtc ${datadir}/oprofile/stl.pat ${datadir}/oprofile/${OPROFILE_ARCH}"
