include ${PN}.inc
    
PV = "${OPIE_CVS_PV}"

SRC_URI = "${HANDHELDS_CVS};module=opie/core/multimedia/opieplayer \
	   file://libmadplugin.pro"
