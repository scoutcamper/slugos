require linux.inc

DESCRIPTION = "Linux kernel for OMAP processors"
KERNEL_IMAGETYPE = "uImage"

COMPATIBLE_MACHINE = "omap5912osk|omap1710h3|omap2430sdp|omap2420h4|beagleboard|omap3evm|omap3-pandora|overo"

DEFAULT_PREFERENCE = "-1"
DEFAULT_PREFERENCE_omap3evm = "1"
DEFAULT_PREFERENCE_omap3-pandora = "1"


SRCREV = "45e5c5ffd32ade5a21a5e87b4040072590ec3ae1"

#PV = "2.6.27+2.6.28-rc8+${PR}+gitr${SRCREV}"
PV = "2.6.28+2.6.29-rc2${PR}+gitr${SRCREV}"
PR = "r10"

SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/tmlind/linux-omap-2.6.git;protocol=git \
	   file://defconfig"

SRC_URI_append = " \
           file://no-empty-flash-warnings.patch;patch=1 \
           file://no-cortex-deadlock.patch;patch=1 \
           file://read_die_ids.patch;patch=1 \
           file://fix-install.patch;patch=1 \
           file://musb-support-high-bandwidth.patch.eml;patch=1 \
           file://mru-fix-timings.diff;patch=1 \
           file://mru-fix-display-panning.diff;patch=1 \
           file://mru-improve-pixclock-config.diff;patch=1 \
           file://mru-make-video-timings-selectable.diff;patch=1 \
           file://mru-enable-overlay-optimalization.diff;patch=1 \
           file://0001-Removed-resolution-check-that-prevents-scaling-when.patch;patch=1 \
           file://0001-Implement-downsampling-with-debugs.patch;patch=1 \
           file://0001-DSS-New-display-subsystem-driver-for-OMAP2-3.patch;patch=1 \
           file://0002-DSS-OMAPFB-fb-driver-for-new-display-subsystem.patch;patch=1 \
           file://0003-DSS-Add-generic-DVI-panel.patch;patch=1 \
           file://0004-DSS-support-for-Beagle-Board.patch;patch=1 \
           file://0005-DSS-Sharp-LS037V7DW01-LCD-Panel-driver.patch;patch=1 \
           file://0006-DSS-Support-for-OMAP3-SDP-board.patch;patch=1 \
           file://0007-DSS-Support-for-OMAP3-EVM-board.patch;patch=1 \
           file://0008-DSS-Hacked-N810-support.patch;patch=1 \
           file://0009-DSS-OMAPFB-allocate-fbmem-only-for-fb0-or-if-spes.patch;patch=1 \
           file://0010-DSS-OMAPFB-remove-extra-omapfb_setup_overlay-call.patch;patch=1 \
           file://0011-DSS-OMAPFB-fix-GFX_SYNC-to-be-compatible-with-DSS1.patch;patch=1 \
           file://0012-DSS-Add-comments-to-FAKE_VSYNC-to-make-things-more.patch;patch=1 \
           file://0013-DSS-OMAPFB-remove-extra-spaces.patch;patch=1 \
           file://0014-DSS-fix-clk_get_usecount.patch;patch=1 \
#           file://0001-ASoC-Add-support-for-OMAP3-EVM.patch;patch=1 \
           file://0001-This-merges-Steve-Kipisz-USB-EHCI-support.-He-star.patch;patch=1 \
           file://0001-board-omap3beagle-set-i2c-3-to-100kHz.patch;patch=1 \
"


SRC_URI_append_beagleboard = " file://logo_linux_clut224.ppm \
			     "

SRC_URI_append_omap3evm = " \
	file://evm-mcspi-ts.diff;patch=1 \
"

S = "${WORKDIR}/git"


module_autoload_ohci-hcd_omap5912osk = "ohci-hcd"


