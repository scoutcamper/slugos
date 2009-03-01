DESCRIPTION = "The X Window System -- install this task to get a client/server based display multiplexer."
SECTION = "x11/server"
LICENSE = "MIT"
PV = "1.0"
PR = "r2"

# WORK IN PROGRESS

inherit task

PACKAGES += "\
  ${PN}-server \
  ${PN}-utils \
"

RRECOMMENDS_${PN} = "\
  ${PN}-server \
  ${PN}-utils \
"

# Some machines don't set a *runtime* provider for X, so default to Xfbdev here
# virtual/xserver won't work, since the kdrive recipes will build multiple xserver packages
XSERVER ?= "xserver-kdrive-fbdev"

# This is also the reason why we have to make this package machine specific :/
PACKAGE_ARCH_${PN}-server = "${MACHINE_ARCH}"

RDEPENDS_${PN}-server = "\
  ${XSERVER} \
"

RDEPENDS_${PN}-utils = "\
  xserver-kdrive-common \
  xserver-nodm-init \
  xauth \
  xhost \
  xset \
  xrandr \
"

