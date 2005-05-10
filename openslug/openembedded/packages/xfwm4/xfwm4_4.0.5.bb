# xfwm4 OE build file
# Copyright (C) 2004, Advanced Micro Devices, Inc.  All Rights Reserved
# Released under the MIT license (see packages/COPYING)

DESCRIPTION="XFCE4 Window Manager"
SECTION = "x11/wm"

PACKAGES="xfwm4 xfwm4-dev ${PN}-doc xfwm4-mcs-plugins"

FILES_${PN}="${bindir}/* ${datadir}/xfwm4/defaults ${datadir}/xfwm4/themes/default.keys/*"
FILES_xfwm4-mcs-plugins="${libdir}/xfce4/mcs-plugins/*.so"

DEPENDS="startup-notification x11 xpm libxfce4util libxfcegui4 libxfce4mcs xfce-mcs-manager"

inherit xfce

EXTRA_OECONF=" --enable-startup-notification"

python populate_packages_prepend () {
	themedir = bb.data.expand('${datadir}/xfwm4/themes', d)
	do_split_packages(d, themedir, '^(.*)', 'xfwm4-theme-%s', 'XFWM4 theme %s', allow_dirs=True)
}
