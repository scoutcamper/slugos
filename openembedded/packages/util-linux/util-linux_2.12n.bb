inherit autotools
include util-linux.inc

SRC_URI = "ftp://ftp.kernel.org/pub/linux/utils/util-linux/util-linux-${PV}.tar.bz2 \
           file://MCONFIG \
           file://make_include \
           file://swapargs.h \
           file://defines.h"
