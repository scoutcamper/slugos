include ${PN}.inc

PR = "r1"

SRC_URI = "${HANDHELDS_CVS};tag=${TAG};module=opie/libopie2 \
	   file://openzaurus-branding.patch;patch=1 \
           file://include.pro"
