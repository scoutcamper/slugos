# Makefile for OpenSlug
# Licensed under the GPL v2 or later

BITBAKE_TAG = -r 154

openslug-firmware: bitbake setup-env conf/local.conf
	(source setup-env ; bitbake openslug-packages)

bitbake:
	svn co ${BITBAKE_TAG} svn://svn.berlios.de/bitbake/trunk/bitbake

setup-env:
	echo 'OEROOT='`pwd` > setup-env
	echo 'OESYS=$$OEROOT/bitbake/' >> setup-env
	echo 'PKGDIR=$$OEROOT/openembedded/' >> setup-env
	echo 'OEBUILD=$$OEROOT' >> setup-env
	echo 'BBPATH=$$OEBUILD:$$PKGDIR:$$OESYS' >> setup-env
	echo 'PATH=$$OESYS/bin/:$$PATH' >> setup-env
	echo 'LD_LIBRARY_PATH=' >> setup-env
	echo 'export PATH LD_LIBRARY_PATH BBPATH' >> setup-env
	echo 'export LANG=C' >> setup-env
	echo 'alias bb=bitbake' >> setup-env
	echo 'echo Environment set up for OpenSlug development.' >> setup-env

conf/local.conf:
	sed -e "s|%%%OEROOT%%%|`pwd`|" conf/local.conf.template > conf/local.conf

clobber:
	rm -rf bitbake tmp

update-ignore:
	svn propset svn:ignore -F .svnignore .

openslug-source:
	tar zcvf openslug-source.tar.gz --exclude=.svn Makefile conf openembedded nslu2-linux

# End of Makefile
