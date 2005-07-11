SECTION = "kernel"
DESCRIPTION = "Linux kernel for VIA EPiA"
LICENSE = "GPL"
PR = "r0"

SRC_URI = "ftp://ftp.kernel.org/pub/linux/kernel/v2.6/linux-${PV}.tar.bz2 \
	   http://hem.bredband.net/ekmlar/patch-vt1211-2.6.txt;patch=1 \
           file://epia_defconfig"
S = "${WORKDIR}/linux-${PV}"

COMPATIBLE_HOST = 'i.86.*-linux'

inherit kernel

ARCH = "i386"
KERNEL_IMAGETYPE = "bzImage"

# Don't want kernel in rootfs
FILES_kernel = ""
ALLOW_EMPTY_kernel = "1"
PACKAGES += "kernel-image"
FILES_kernel-image = "/boot/${KERNEL_IMAGETYPE}*"

do_configure_prepend() {
	install -m 0644 ${WORKDIR}/epia_defconfig ${S}/.config
}