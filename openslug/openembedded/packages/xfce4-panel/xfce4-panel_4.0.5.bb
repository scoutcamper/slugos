# xfce4-panel OE build file
# Copyright (C) 2004, Advanced Micro Devices, Inc.  All Rights Reserved
# Released under the MIT license (see packages/COPYING)

DESCRIPTION="XFCE4 Panel"
SECTION = "x11"
DEPENDS="startup-notification x11 libxfcegui4 libxfce4mcs xfce-mcs-manager libxml2"

PACKAGES = "xfce4-panel xfce4-panel-dev ${PN}-doc \
	    xfce4-panel-plugins xfce4-panel-mcs-plugins"

FILES_${PN}="${bindir} /etc"
FILES_xfce4-panel-plugins="${libdir}/xfce4/panel-plugins/*.so"
FILES_xfce4-panel-mcs-plugins="${libdir}/xfce4/mcs-plugins/*.so"

inherit xfce pkgconfig

EXTRA_OECONF=" --enable-startup-notification"

do_install() {
	oe_runmake DESTDIR=${D} install
}

python populate_packages_prepend () {
	themedir = bb.data.expand('${datadir}/xfce4/themes', d)
	do_split_packages(d, themedir, '^(.*)', 'xfce4-panel-theme-%s', 'XFCE4 panel theme %s', allow_dirs=True)
}	

HEADERS="controls.h global.h icons.h main.h panel.h item.h \
	item_dialog.h plugins.h xfce_support.h xfce.h"

do_stage() {
	install -d ${STAGING_INCDIR}/xfce4/panel

	for file in ${HEADERS}; do 
		install -m 644 ${S}/panel/$file ${STAGING_INCDIR}/xfce4/panel/$file
	done
}
