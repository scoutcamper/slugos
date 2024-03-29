DESCRIPTION = "BusyBox combines tiny versions of many common UNIX utilities into a single \
small executable. It provides minimalist replacements for most of the \
utilities you usually find in GNU fileutils, shellutils, etc. The utilities \
in BusyBox generally have fewer options than their full-featured GNU \
cousins; however, the options that are included provide the expected \
functionality and behave very much like their GNU counterparts. BusyBox \
provides a fairly complete POSIX environment for any small or embedded \
system."
HOMEPAGE = "http://www.busybox.net"
LICENSE = "GPL"
SECTION = "base"
PRIORITY = "required"

SRC_URI = "\
  http://www.busybox.net/downloads/busybox-${PV}.tar.gz \
  file://busybox-cron \
  file://busybox-httpd \
  file://busybox-udhcpd \
  file://default.script \
  file://dhcp-hostname.patch;patch=1 \
  file://hwclock.sh \
  file://ifupdown-spurious-environ.patch;patch=1 \
  file://mount.busybox \
  file://syslog \
  file://syslog.conf \
  file://udhcpscript.patch;patch=1 \
  file://umount.busybox \
"

SRC_URI_append_nylon = " file://xargs-double-size.patch;patch=1"

export EXTRA_CFLAGS = "${CFLAGS}"
EXTRA_OEMAKE_append = " CROSS=${HOST_PREFIX}"
PACKAGES =+ "${PN}-httpd ${PN}-udhcpd"
# We need this RRECOMMENDS because libc dlopens libgcc
# and shlib mechanism can not detect it because its not
# listed in the NEEDED field.
RRECOMMENDS += "libgcc"
FILES_${PN}-httpd = "${sysconfdir}/init.d/busybox-httpd /srv/www"
FILES_${PN}-udhcpd = "${sysconfdir}/init.d/busybox-udhcpd"

FILES_${PN} += "${datadir}/udhcpc"

INITSCRIPT_PACKAGES = "${PN} ${PN}-httpd ${PN}-udhcpd"
INITSCRIPT_NAME_${PN}-httpd = "busybox-httpd"
INITSCRIPT_NAME_${PN}-udhcpd = "busybox-udhcpd" 
INITSCRIPT_NAME_${PN} = "syslog"
CONFFILES_${PN} = "${sysconfdir}/syslog.conf"

# This disables the syslog startup links in slugos (see slugos-init)
INITSCRIPT_PARAMS_${PN}_slugos = "start 20 ."

# Use gcc for linking so LDFLAGS actually makes sense
LD = "${CC} -nostdlib"

inherit cml1 update-rc.d

do_compile() {
	unset CFLAGS CPPFLAGS CXXFLAGS
	base_do_compile
}

do_install () {
	oe_runmake busybox.links
	if [ "x${layout_prefix}" = "x" ]; then
		sed 's:^/usr/:/:' < busybox.links >busybox.links.new
		mv busybox.links.new busybox.links
        elif [ "${layout_prefix}" != "/usr" ]; then
		echo "warning, busybox.links will lose with this prefix"
	fi
	unset CFLAGS CPPFLAGS CXXFLAGS
	install -d ${D}${sysconfdir}/init.d
	oe_runmake "PREFIX=${D}" install
	cp -pPR ${S}/_install/* ${D}/

	# Move everything to /busybox (not supposed to end up in any package)
	install -d ${D}/busybox
	ls ${D} -R

	for i in ${D}${base_bindir} ${D}${base_sbindir} ${D}${prefix} ; do
		if [ -d $i ]; then
			cp -dPr $i ${D}/busybox/
		fi
	done
	# Move the busybox binary back to /bin
	install -d ${D}${base_bindir}
	mv ${D}/busybox${base_bindir}/busybox ${D}${base_bindir}/
	# Move back the sh symlink
	test -h ${D}/busybox${base_bindir}/sh && mv ${D}/busybox${base_bindir}/sh ${D}${base_bindir}/

	install -m 0755 ${WORKDIR}/syslog ${D}${sysconfdir}/init.d/
	install -m 644 ${WORKDIR}/syslog.conf ${D}${sysconfdir}/
	if grep "CONFIG_CROND=y" ${WORKDIR}/defconfig; then
		# Move crond back to /usr/sbin/crond
		install -d ${D}${sbindir}
		mv ${D}/busybox${sbindir}/crond ${D}${sbindir}/

		install -m 0755 ${WORKDIR}/busybox-cron ${D}${sysconfdir}/init.d/
	fi
	if grep "CONFIG_HTTPD=y" ${WORKDIR}/defconfig; then
		# Move httpd back to /usr/sbin/httpd
		install -d ${D}${sbindir}
		mv ${D}/busybox${sbindir}/httpd ${D}${sbindir}/

		install -m 0755 ${WORKDIR}/busybox-httpd ${D}${sysconfdir}/init.d/
		install -d ${D}/srv/www
	fi
	if grep "CONFIG_APP_UDHCPD=y" ${WORKDIR}/defconfig; then
		# Move udhcpd back to /usr/sbin/udhcpd
		install -d ${D}${sbindir}
		mv ${D}/busybox${sbindir}/udhcpd ${D}${sbindir}/

		install -m 0755 ${WORKDIR}/busybox-udhcpd ${D}${sysconfdir}/init.d/
	fi
	if grep "CONFIG_HWCLOCK=y" ${WORKDIR}/defconfig; then
		# Move hwclock back to /sbin/hwclock
		install -d ${D}${base_sbindir}
		mv ${D}/busybox${base_sbindir}/hwclock ${D}${base_sbindir}/

		install -m 0755 ${WORKDIR}/hwclock.sh ${D}${sysconfdir}/init.d/
	fi
	if grep "CONFIG_APP_UDHCPC=y" ${WORKDIR}/defconfig; then
		# Move dhcpc back to /usr/sbin/udhcpc
		install -d ${D}${base_sbindir}
		mv ${D}/busybox${base_sbindir}/udhcpc ${D}${base_sbindir}/

		install -d ${D}${sysconfdir}/udhcpc.d
		install -d ${D}${datadir}/udhcpc
		install -m 0755 ${S}/examples/udhcp/simple.script ${D}${sysconfdir}/udhcpc.d/50default
		install -m 0755 ${WORKDIR}/default.script ${D}${datadir}/udhcpc/default.script
	fi
	if grep "CONFIG_FEATURE_MOUNT_FSTAB=y" ${WORKDIR}/defconfig; then
		install -d ${D}${sysconfdir}/default
		install -m 644 ${WORKDIR}/mountall ${D}${sysconfdir}/default/
	fi

	install -m 0644 ${S}/busybox.links ${D}${sysconfdir}
}

pkg_postinst_${PN} () {
	# If we are not making an image we create links for the utilities that doesn't exist
	# so the update-alternatives script will get the utilities it needs
	# (update-alternatives have no problem replacing links later anyway)
	test -n 2> /dev/null || alias test='busybox test'
	if test "x$D" = "x"; then while read link; do if test ! -h "$link"; then case "$link" in /*/*/*) to="../../bin/busybox";; /bin/*) to="busybox";; /*/*) to="../bin/busybox";; esac; busybox ln -s $to $link; fi; done </etc/busybox.links; fi
	
	# This adds the links, remember that this has to work when building an image too, hence the $D
	while read link; do case "$link" in /*/*/*) to="../../bin/busybox";; /bin/*) to="busybox";; /*/*) to="../bin/busybox";; esac; bn=`basename $link`; update-alternatives --install $link $bn $to 50; done <$D/etc/busybox.links
}

pkg_prerm_${PN} () {
	# This is so you can make busybox commit suicide - removing busybox with no other packages
	# providing its files, this will make update-alternatives work, but the update-rc.d part
	# for syslog, httpd and/or udhcpd will fail if there is no other package providing sh
	tmpdir=`mktemp -d /tmp/busyboxrm-XXXXXX`
	ln -s /bin/busybox $tmpdir/[
	ln -s /bin/busybox $tmpdir/test
	ln -s /bin/busybox $tmpdir/head
	ln -s /bin/busybox $tmpdir/sh
	ln -s /bin/busybox $tmpdir/basename
	ln -s /bin/busybox $tmpdir/echo
	ln -s /bin/busybox $tmpdir/mv
	ln -s /bin/busybox $tmpdir/ln
	ln -s /bin/busybox $tmpdir/dirname
	ln -s /bin/busybox $tmpdir/rm
	ln -s /bin/busybox $tmpdir/sed
	ln -s /bin/busybox $tmpdir/sort
	export PATH=$PATH:$tmpdir

	while read link
	do
		case "$link" in
			/*/*/*) to="../../bin/busybox";;
			/bin/*) to="busybox";;
			/*/*) to="../bin/busybox";;
		esac
		bn=`basename $link`
		sh /usr/bin/update-alternatives --remove $bn $to
	done </etc/busybox.links
}

PACKAGES =+ "${PN}-mdev"
FILES_${PN}-mdev = "${sysconfdir}/mdev ${sysconfdir}/mdev.conf ${sysconfdir}/init.d/mdev"
RDEPENDS_${PN}-mdev += "${PN}"

pkg_postinst_${PN}-mdev() {
if test "x$D" != "x"; then
	OPT="-r $D"
else
	OPT="-s"
fi
	update-rc.d $OPT mdev start 06 S .
}



