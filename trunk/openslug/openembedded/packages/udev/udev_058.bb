SRC_URI = "http://kernel.org/pub/linux/utils/kernel/hotplug/udev-${PV}.tar.gz \
	   file://tmpfs.patch;patch=1 \
	   file://noasmlinkage.patch;patch=1 \
	   file://flags.patch;patch=1 \
	   file://init"

include udev.inc

PR = "r3"
# CFLAGS += -I${STAGING_INCDIR}/sysfs
