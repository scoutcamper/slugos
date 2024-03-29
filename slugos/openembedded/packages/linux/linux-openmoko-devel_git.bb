require linux.inc
require linux-openmoko.inc

DEFAULT_PREFERENCE = "-1"

KERNEL_RELEASE = "2.6.29"
KERNEL_VERSION = "${KERNEL_RELEASE}"

OEV = "oe0"
PV = "${KERNEL_RELEASE}-${OEV}+gitr${SRCREV}"
PR = "r1"

SRC_URI = "\
  git://git.openmoko.org/git/kernel.git;protocol=git;branch=andy-tracking \
#  file://openwrt-ledtrig-netdev.patch;patch=1 \
#  file://defconfig-oe.patch \
"
S = "${WORKDIR}/git"

CONFIG_NAME_om-gta01 = "gta01_moredrivers_defconfig"
CONFIG_NAME_om-gta02 = "gta02_packaging_defconfig"
CONFIG_NAME_om-gta03 = "gta03_defconfig"

do_configure_prepend() {
	install -m 644 ./arch/arm/configs/${CONFIG_NAME} ${WORKDIR}/defconfig-oe
	cat ${WORKDIR}/defconfig-oe.patch | patch -p0 -d ${WORKDIR}
}
