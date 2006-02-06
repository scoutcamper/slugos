LICENSE = "GPL"
SECTION = "x11/gnome"
PR = "r0"
PROVIDES = "gnome-vfs"
RPROVIDES = "gnome-vfs"

DEFAULT_PREFERENCE = "-1"

inherit gnome pkgconfig

DEPENDS = "gtk-doc-native libxml2 gconf-dbus dbus bzip2 gnome-mime-data zlib samba-3.0.14a"
RRECOMMENDS = "gnome-vfs-plugin-file shared-mime-info"


SRC_URI = "svn://developer.imendio.com/svn/gnome-vfs-dbus;module=trunk;proto=http \
           file://gssapi.patch;patch=1;pnum=1 \
           file://gconftool-lossage.patch;patch=1;pnum=1 \
	   file://werror_compile.patch;patch=1;pnum=1 \
	   file://gtk-doc.m4 \
	   file://gtk-doc.make"

EXTRA_OECONF = "--with-ipc=dbus --disable-gtk-doc"
S = "${WORKDIR}/trunk"

FILES_${PN} += " ${libdir}/vfs"
FILES_${PN}-dev += " ${libdir}/gnome-vfs-2.0/modules/*.a ${libdir}/gnome-vfs-2.0/modules/*.la ${libdir}/gnome-vfs-2.0/include"
#FILES_${PN}-doc += " ${datadir}/gtk-doc"

GNOME_VFS_HEADERS = " \
gnome-vfs-utils.h \
gnome-vfs-application-registry.h \
gnome-vfs-async-ops.h \
gnome-vfs-ops.h \
gnome-vfs-uri.h \
gnome-vfs-standard-callbacks.h \
gnome-vfs-module-callback.h \
gnome-vfs-context.h \
gnome-vfs-file-info.h \
gnome-vfs-directory.h \
gnome-vfs-mime-monitor.h \
gnome-vfs-mime-handlers.h \
gnome-vfs-mime-deprecated.h \
gnome-vfs-result.h \
gnome-vfs-job-limit.h \
gnome-vfs-file-size.h \
gnome-vfs-mime-utils.h \
gnome-vfs-find-directory.h \
gnome-vfs-init.h \
gnome-vfs-handle.h \
gnome-vfs.h \
gnome-vfs-cancellation.h \
gnome-vfs-xfer.h \
gnome-vfs-monitor.h \
gnome-vfs-types.h \
gnome-vfs-volume-monitor.h \
gnome-vfs-drive.h \
gnome-vfs-volume.h \
gnome-vfs-enum-types.h \
gnome-vfs-address.h \
gnome-vfs-dns-sd.h \
gnome-vfs-mime-info-cache.h \
gnome-vfs-resolve.h"

GNOME_VFS_MODULE_HEADERS = " \
gnome-vfs-mime-info.h \
gnome-vfs-transform.h \
gnome-vfs-ssl.h \
gnome-vfs-inet-connection.h \
gnome-vfs-socket.h \
gnome-vfs-parse-ls.h \
gnome-vfs-method.h \
gnome-vfs-cancellable-ops.h \
gnome-vfs-module.h \
gnome-vfs-module-shared.h \
gnome-vfs-module-callback-module-api.h \
gnome-vfs-mime.h \
gnome-vfs-socket-buffer.h"

do_configure_prepend() {
	mkdir -p m4
	install ${WORKDIR}/gtk-doc.m4 ./m4/
	install ${WORKDIR}/gtk-doc.make ./
}

do_stage() {
	oe_libinstall -so -C libgnomevfs libgnomevfs-2 ${STAGING_LIBDIR}
	install -d ${STAGING_INCDIR}/gnome-vfs-2.0/libgnomevfs
	for i in ${GNOME_VFS_HEADERS}; do install -m 0644 libgnomevfs/$i ${STAGING_INCDIR}/gnome-vfs-2.0/libgnomevfs/; done
	install -d ${STAGING_INCDIR}/gnome-vfs-module-2.0/libgnomevfs
	for i in ${GNOME_VFS_MODULE_HEADERS}; do install -m 0644 libgnomevfs/$i ${STAGING_INCDIR}/gnome-vfs-module-2.0/libgnomevfs/; done
}

do_install() {
	oe_runmake ORBIT_IDL="${ORBIT_IDL_SRC}" DESTDIR="${D}" install
}

PACKAGES_DYNAMIC = "gnome=vfs-plugin-*"

python populate_packages_prepend () {
	print bb.data.getVar('FILES_gnome-vfs', d, 1)

	plugindir = bb.data.expand('${libdir}/gnome-vfs-2.0/modules/', d)
	do_split_packages(d, plugindir, '^lib(.*)\.so$', 'gnome-vfs-plugin-%s', 'GNOME VFS plugin for %s')
}
