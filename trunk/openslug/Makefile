# Makefile for OpenSlug
# Licensed under the GPL v2 or later

openslug-firmware: setup-env conf/local.conf downloads/flex-2.5.31.tar.bz2
	(source setup-env ; bitbake openslug-packages)

downloads/flex-2.5.31.tar.bz2:
	test -d downloads || mkdir downloads
	cd downloads; wget -nc http://nslu.sourceforge.net/downloads/flex-2.5.31.tar.bz2

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
	echo 'unset LC_CTYPE' >> setup-env
	echo 'unset CC' >> setup-env
	echo 'unset CXX' >> setup-env
	echo 'alias bb=bitbake' >> setup-env
	echo 'echo Environment set up for OpenSlug development.' >> setup-env

conf/local.conf:
	sed -e "s|%%%OEROOT%%%|`pwd`|" conf/local.conf.template > conf/local.conf

clobber:
	rm -rf tmp

update-ignore:
	svn propset svn:ignore -F .svnignore .

openslug-source:
	tar zcvf openslug-source.tar.gz --exclude=.svn Makefile bitbake conf openembedded nslu2-linux

distclean: clobber
	rm -rf setup-env conf/local.conf downloads

# End of Makefile
