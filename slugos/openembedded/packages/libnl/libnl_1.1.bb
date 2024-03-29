DESCRIPTION = "libnl is a library for applications dealing with netlink sockets"
SECTION = "libs/network"
LICENSE = "LGPL"
HOMEPAGE = "http://people.suug.ch/~tgr/libnl"
PR = "r1"

inherit autotools pkgconfig

CFLAGS += '-DVLAN_FLAG_REORDER_HDR=1'

SRC_URI = "\
  http://people.suug.ch/~tgr/libnl/files/libnl-${PV}.tar.gz \
  file://local-includes-and-avoid-wrong-ldflags.patch;patch=1 \
  file://fix-includes.patch;patch=1 \
"

do_stage () {
	autotools_stage_all prefix=${prefix}
}
