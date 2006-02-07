#!/bin/make
# Makefile for OpenEmbedded builds
# Licensed under the GPL v2 or later
#
# Use "make help" for more information!
#
# conf/auto.conf must exist in the same directory as the Makefile (i.e.
# the directory where Makefile is used - it may be a symbolic link to
# this file).  conf/auto.conf defines:
#
# DISTRO - the OpenEmbedded 'distro' to build
# MACHINE - the OpenEmbedded build target machine
#
# All of these values are (should be, must be) quoted in double quotes
-include conf/auto.conf

BUILD_DIRS = downloads
REQUIRED_DIRS = bitbake openembedded
FIRMWARE_DEPS = create-topdir $(BUILD_DIRS) $(REQUIRED_DIRS)
BITBAKE = bitbake
TARGET = image

# the default makes auto.conf
.PHONY: configuration
configuration:
	@if test -n "$(DISTRO)";\
	then\
		{ echo 'DISTRO="'$(DISTRO)'"'; echo 'MACHINE="nslu2"'; } >conf/auto.new;\
	else\
		echo>&2;\
		echo '*** SlugOS: use make DISTRO="<distro>" to initialise ***'>&2;\
		echo '***  use make help to get more information           ***'>&2;\
		echo>&2;\
		exit 1; \
	fi
	@if test -e conf/auto.conf && cmp -s conf/auto.conf conf/auto.new;\
	then\
		rm -f conf/auto.new;\
		echo '*** SlugOS: DISTRO is "'$(DISTRO)'"';\
	else\
		test -e conf/auto.conf && {\
			echo '*** SlugOS: WARNING: DISTRO changed from:'>&2;\
			cat conf/auto.conf | sed 's/^/   /' >&2;\
			echo '*** WARNING: parallel builds with a different DISTROs will NOT WORK'>&2;\
		};\
		mv conf/auto.new conf/auto.conf;\
		echo '*** SlugOS: DISTRO set to "'$(DISTRO)'"';\
	fi
	@make $(TARGET)-safe

# This rule clobbers the environment (note that ccache uses '$HOME' by
# default, so the cache will end up there).
.PHONY: distro image kernel prefetch
distro image kernel prefetch: $$@-safe

%-config:
	@make TARGET=config DISTRO="$*" configuration
%-distro:
	@make TARGET=distro DISTRO="$*" configuration
%-image:
	@make TARGET=image DISTRO="$*" configuration
%-kernel:
	@make TARGET=kernel DISTRO="$*" configuration
%-prefetch:
	@make TARGET=prefetch DISTRO="$*" configuration

config-safe:
	@echo '*** SlugOS: configuration created:'
	@cat conf/auto.conf | sed 's/^/   /'

%-safe:
	@echo '*** SlugOS: building '$(DISTRO)' $* for '$(MACHINE)' in a safe environment'
	env -i HOME="$${HOME}" PATH="$${PATH}" $(MAKE) $*-unsafe

# Using the following is not recommended
distro-unsafe: $(FIRMWARE_DEPS)
	. ./setup-env; exec ${BITBAKE} $(DISTRO)-packages

image-unsafe: $(FIRMWARE_DEPS)
	. ./setup-env; exec ${BITBAKE} $(DISTRO)-image

kernel-unsafe: $(FIRMWARE_DEPS)
	. ./setup-env; exec ${BITBAKE} virtual/kernel

prefetch-unsafe: $(FIRMWARE_DEPS)
	. ./setup-env; exec ${BITBAKE} -cfetch $(DISTRO)-packages

# topdir.conf is re-created automatically if the directory is
# moved - this will cause a full bitbake reparse
.PHONY: create-topdir
create-topdir: conf/topdir.conf
	. conf/topdir.conf && test "`pwd`" = "$$TOPDIR" || echo "TOPDIR='`pwd`'" > conf/topdir.conf

conf/topdir.conf:
	echo "TOPDIR='`pwd`'" >$@

# rules for directories - if a symlink exists and the target does not
# exist something will go wrong in the build, therefore cause a failure
# here by the mkdir.
$(BUILD_DIRS):
	test -d $@ || if test -d ../$@; then ln -s ../$@ .; else mkdir $@; fi

# these directories must already exist - either in TOPDIR (here) or in ..
$(REQUIRED_DIRS):
	test -d $@ || if test -d ../$@; then ln -s ../$@ .; else exit 1; fi

.PHONY: clobber
clobber:
	rm -rf *.tmp

.PHONY: source
source: $(REQUIRED_DIRS)
	tar zcf $(DISTRO).tar.gz --exclude=MT --exclude=.svn Makefile setup-env \
		conf/site.conf conf/local.conf.sample $(REQUIRED_DIRS:=/.)

# This target probably isn't important any longer, because the -source
# target above does the right thing
.PHONY: distclean
distclean: clobber
	rm -rf conf/topdir.conf conf/local.conf $(BUILD_DIRS)

# This target is mainly for testing - it is intended to put the disto directory
# back to its original state, it will destroy a source-tarball system (because
# it removes directories from the tarball).
.PHONY: really-clean
really-clean: distclean
	rm -rf $(REQUIRED_DIRS) $(DISTRO)-source.tar.gz

.PHONY: help
help:
	@echo "SlugOS Makefile:"
	@echo " make DISTRO=<distro> to initialise"
	@echo "OR:"
	@echo " make <distro>-<target> where target may be:"
	@echo "  prefetch: just download the source archives"
	@echo "  kernel: just the kernel"
	@echo "  image: a flash file system root image and the kernel"
	@echo "  distro: the full distribution including packages"
	@echo
	@echo " the default is 'image'.  distros are:"
	@echo "   slugos-[bl][at][ug] - base distros"
	@echo "   openslug (slugos-bag with file system support)"
	@echo "   ucslugc (a synonym for slugos-btu)"
	@echo "   debianslug (slugos-lag with file system support)"
	@echo "   slugos-ltu - the only other tested distro..."
	@echo
	@echo "See http://www.nslu2-linux.org for more information"
