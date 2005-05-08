# Makefile for OpenSlug

BITBAKE_TAG = -r 154

openslug-firmware:
	(source setup-env ; bitbake openslug-packages)

openslug-source:
	tar zcvf openslug-source.tar.gz --exclude=.svn Makefile conf openembedded nslu2-linux

bitbake:
	svn co ${BITBAKE_TAG} svn://svn.berlios.de/bitbake/trunk/bitbake

clobber:
	rm -rf bitbake tmp

update-ignore:
	svn propset svn:ignore -F .svnignore .
