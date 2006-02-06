DESCRIPTION = "EET is a tiny library designed to write an \
arbitary set of chunks of data to a file and optionally compress \
each chunk (very much like a zip file) and allow fast \
random-access reading of the file later on."
DEPENDS = "zlib jpeg"
LICENSE = "BSD"
PR = "r1"

inherit efl

SRC_URI = "cvs://anonymous@thinktux.net/root;module=e17/libs/eet;date=${PV}"
S = "${WORKDIR}/eet"
