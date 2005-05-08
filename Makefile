BITBAKE_TAG = -r 154

bitbake:
	svn co ${BITBAKE_TAG} svn://svn.berlios.de/bitbake/trunk/bitbake

clobber:
	rm -rf bitbake tmp