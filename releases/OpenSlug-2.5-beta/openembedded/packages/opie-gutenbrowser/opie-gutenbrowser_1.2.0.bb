include ${PN}.inc
    
 
PR = "r0"

SRC_URI = "${HANDHELDS_CVS};tag=${TAG};module=opie/noncore/apps/opie-gutenbrowser \
           ${HANDHELDS_CVS};tag=${TAG};module=opie/pics \
           ${HANDHELDS_CVS};tag=${TAG};module=opie/apps \
	   ${HANDHELDS_CVS};tag=${TAG};module=opie/etc"
