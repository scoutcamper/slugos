include ${PN}.inc
    
PV = "1.3.0+cvs-${CVSDATE}"
PR = "r0"

SRC_URI = "${HANDHELDS_CVS};module=opie/noncore/applets/pcmcia \
	   ${HANDHELDS_CVS};module=opie/pics"
