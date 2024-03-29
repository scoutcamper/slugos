inherit base

# use autotools_stage_all for native packages
AUTOTOOLS_NATIVE_STAGE_INSTALL = "1"

def autotools_dep_prepend(d):
	import bb;

	if bb.data.getVar('INHIBIT_AUTOTOOLS_DEPS', d, 1):
		return ''

	pn = bb.data.getVar('PN', d, 1)
	deps = ''

	if pn in ['autoconf-native', 'automake-native']:
		return deps
	deps += 'autoconf-native automake-native '

	if not pn in ['libtool', 'libtool-native', 'libtool-cross']:
		deps += 'libtool-native '
		if not bb.data.inherits_class('native', d) \
                        and not bb.data.inherits_class('cross', d) \
                        and not bb.data.getVar('INHIBIT_DEFAULT_DEPS', d, 1):
                    deps += 'libtool-cross '

	return deps + 'gnu-config-native '

EXTRA_OEMAKE = ""
DEPENDS_prepend = "${@autotools_dep_prepend(d)}"
acpaths = "default"
EXTRA_AUTORECONF = "--exclude=autopoint"

def autotools_set_crosscompiling(d):
	import bb
	if not bb.data.inherits_class('native', d):
		return " cross_compiling=yes"
	return ""

# EXTRA_OECONF_append = "${@autotools_set_crosscompiling(d)}"

oe_runconf () {
	if [ -x ${S}/configure ] ; then
		cfgcmd="${S}/configure \
		    --build=${BUILD_SYS} \
		    --host=${HOST_SYS} \
		    --target=${TARGET_SYS} \
		    --prefix=${prefix} \
		    --exec_prefix=${exec_prefix} \
		    --bindir=${bindir} \
		    --sbindir=${sbindir} \
		    --libexecdir=${libexecdir} \
		    --datadir=${datadir} \
		    --sysconfdir=${sysconfdir} \
		    --sharedstatedir=${sharedstatedir} \
		    --localstatedir=${localstatedir} \
		    --libdir=${libdir} \
		    --includedir=${includedir} \
		    --oldincludedir=${oldincludedir} \
		    --infodir=${infodir} \
		    --mandir=${mandir} \
			${EXTRA_OECONF} \
		    $@"
		oenote "Running $cfgcmd..."
		$cfgcmd || oefatal "oe_runconf failed" 
	else
		oefatal "no configure script found"
	fi
}

autotools_do_configure() {
	case ${PN} in
	autoconf*)
	;;
	automake*)
	;;
	*)
		# WARNING: gross hack follows:
		# An autotools built package generally needs these scripts, however only
		# automake or libtoolize actually install the current versions of them.
		# This is a problem in builds that do not use libtool or automake, in the case
		# where we -need- the latest version of these scripts.  e.g. running a build
		# for a package whose autotools are old, on an x86_64 machine, which the old
		# config.sub does not support.  Work around this by installing them manually
		# regardless.
		( for ac in `find ${S} -name configure.in -o -name configure.ac`; do
			rm -f `dirname $ac`/configure
			done )
		if [ -e ${S}/configure.in -o -e ${S}/configure.ac ]; then
			olddir=`pwd`
			cd ${S}
			if [ x"${acpaths}" = xdefault ]; then
				acpaths=
				for i in `find ${S} -maxdepth 2 -name \*.m4|grep -v 'aclocal.m4'| \
					grep -v 'acinclude.m4' | sed -e 's,\(.*/\).*$,\1,'|sort -u`; do
					acpaths="$acpaths -I $i"
				done
			else
				acpaths="${acpaths}"
			fi
			AUTOV=`automake --version |head -n 1 |sed "s/.* //;s/\.[0-9]\+$//"`
			automake --version
			echo "AUTOV is $AUTOV"
			install -d ${STAGING_DATADIR}/aclocal
			install -d ${STAGING_DATADIR}/aclocal-$AUTOV
			acpaths="$acpaths -I${STAGING_DATADIR}/aclocal-$AUTOV -I ${STAGING_DATADIR}/aclocal"
			# autoreconf is too shy to overwrite aclocal.m4 if it doesn't look
			# like it was auto-generated.  Work around this by blowing it away
			# by hand, unless the package specifically asked not to run aclocal.
			if ! echo ${EXTRA_AUTORECONF} | grep -q "aclocal"; then
				rm -f aclocal.m4
			fi
			if [ -e configure.in ]; then
			  CONFIGURE_AC=configure.in
			else
			  CONFIGURE_AC=configure.ac
			fi
			if grep "^AM_GLIB_GNU_GETTEXT" $CONFIGURE_AC >/dev/null; then
			  if grep "sed.*POTFILES" $CONFIGURE_AC >/dev/null; then
			    : do nothing -- we still have an old unmodified configure.ac
			  else
			    oenote Executing glib-gettextize --force --copy
			    echo "no" | glib-gettextize --force --copy
			  fi
			fi
			if grep "^[AI][CT]_PROG_INTLTOOL" $CONFIGURE_AC >/dev/null; then
			  oenote Executing intltoolize --copy --force --automake
			  intltoolize --copy --force --automake
			fi
			oenote Executing autoreconf --verbose --install --force ${EXTRA_AUTORECONF} $acpaths
			mkdir -p m4
			autoreconf -Wcross --verbose --install --force ${EXTRA_AUTORECONF} $acpaths || oefatal "autoreconf execution failed."
			cd $olddir
		fi
	;;
	esac
	if [ -e ${S}/configure ]; then
		oe_runconf $@
	else
		oenote "nothing to configure"
	fi
}

autotools_do_install() {
	oe_runmake 'DESTDIR=${D}' install
}

do_install_append() {
        for i in `find ${D} -name "*.la"` ; do \
                sed -i -e '/^dependency_libs=/s,${WORKDIR}[[:alnum:]/\._+-]*/\([[:alnum:]\._+-]*\),${libdir}/\1,g' $i
                sed -i -e s:${CROSS_DIR}/${HOST_SYS}::g $i
                sed -i -e s:${CROSS_DIR}::g $i
                sed -i -e s:${STAGING_LIBDIR}:${libdir}:g $i
                sed -i -e s:${STAGING_DIR_HOST}::g $i
                sed -i -e s:${STAGING_DIR}::g $i
                sed -i -e s:${S}::g $i
                sed -i -e s:${T}::g $i
                sed -i -e s:${D}::g $i
        done
}

STAGE_TEMP="${WORKDIR}/temp-staging"

autotools_stage_includes() {
	if [ "${INHIBIT_AUTO_STAGE_INCLUDES}" != "1" ]
	then
		rm -rf ${STAGE_TEMP}
		mkdir -p ${STAGE_TEMP}
		make DESTDIR="${STAGE_TEMP}" install
		cp -pPR ${STAGE_TEMP}/${includedir}/* ${STAGING_INCDIR}
		rm -rf ${STAGE_TEMP}
	fi
}

autotools_stage_dir() {
	from="$1"
	to="$2"
	# This will remove empty directories so we can ignore them
	rmdir "$from" 2> /dev/null || true
	if [ -d "$from" ]; then
		mkdir -p "$to"
		cp -fpPR "$from"/* "$to"
	fi
}

autotools_stage_all() {
	if [ "${INHIBIT_AUTO_STAGE}" = "1" ]
	then
		return
	fi
	rm -rf ${STAGE_TEMP}
	mkdir -p ${STAGE_TEMP}
	oe_runmake DESTDIR="${STAGE_TEMP}" install
	autotools_stage_dir ${STAGE_TEMP}/${includedir} ${STAGING_INCDIR}
	if [ "${BUILD_SYS}" = "${HOST_SYS}" ]; then
		autotools_stage_dir ${STAGE_TEMP}/${bindir} ${STAGING_DIR_HOST}${layout_bindir}
		autotools_stage_dir ${STAGE_TEMP}/${sbindir} ${STAGING_DIR_HOST}${layout_sbindir}
		autotools_stage_dir ${STAGE_TEMP}/${base_bindir} ${STAGING_DIR_HOST}${layout_base_bindir}
		autotools_stage_dir ${STAGE_TEMP}/${base_sbindir} ${STAGING_DIR_HOST}${layout_base_sbindir}
		autotools_stage_dir ${STAGE_TEMP}/${libexecdir} ${STAGING_DIR_HOST}${layout_libexecdir}
		if [ "${prefix}/lib" != "${libdir}" ]; then
			# python puts its files in here, make sure they are staged as well
			autotools_stage_dir ${STAGE_TEMP}/${prefix}/lib ${STAGING_DIR_HOST}${layout_prefix}/lib
		fi
	fi
	if [ -d ${STAGE_TEMP}/${libdir} ]
	then
		olddir=`pwd`
		cd ${STAGE_TEMP}/${libdir}
		las=$(find . -name \*.la -type f)
		cd $olddir
		echo "Found la files: $las"		 
		for i in $las
		do
			sed -e 's/^installed=yes$/installed=no/' \
			    -e '/^dependency_libs=/s,${WORKDIR}[[:alnum:]/\._+-]*/\([[:alnum:]\._+-]*\),${STAGING_LIBDIR}/\1,g' \
			    -e "/^dependency_libs=/s,\([[:space:]']\)${libdir},\1${STAGING_LIBDIR},g" \
			    -i ${STAGE_TEMP}/${libdir}/$i
		done
		autotools_stage_dir ${STAGE_TEMP}/${libdir} ${STAGING_LIBDIR}
	fi
	# Ok, this is nasty. pkgconfig.bbclass is usually used to install .pc files,
	# however some packages rely on the presence of .pc files to enable/disable
	# their configurataions in which case we better should not install everything
	# unconditionally, but rather depend on the actual results of make install.
	# The good news though: a) there are not many packages doing this and
	# b) packaged staging will fix that anyways. :M:
	if [ "${AUTOTOOLS_STAGE_PKGCONFIG}" = "1" ]
	then
		if [ -e ${STAGE_TEMP}/${libdir}/pkgconfig/ ] ; then
			echo "cp -f ${STAGE_TEMP}/${libdir}/pkgconfig/*.pc ${STAGING_LIBDIR}/pkgconfig/"
			cp -f ${STAGE_TEMP}/${libdir}/pkgconfig/*.pc ${STAGING_LIBDIR}/pkgconfig/
		fi
	fi
	rm -rf ${STAGE_TEMP}/${mandir} || true
	rm -rf ${STAGE_TEMP}/${infodir} || true
	autotools_stage_dir ${STAGE_TEMP}/${datadir} ${STAGING_DATADIR}
	rm -rf ${STAGE_TEMP}
}

EXPORT_FUNCTIONS do_configure do_install

