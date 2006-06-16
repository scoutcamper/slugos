include linux-openzaurus.inc

PR = "r8"

DEFAULT_PREFERENCE = "-1"

# Handy URLs
# http://www.kernel.org/pub/linux/kernel/people/alan/linux-2.6/2.6.10/patch-2.6.10-ac8.gz;patch=1 \
# http://www.kernel.org/pub/linux/kernel/v2.6/snapshots/patch-2.6.12-rc4-git1.bz2;patch=1 \
# ftp://ftp.kernel.org/pub/linux/kernel/v2.6/testing/patch-2.6.14-rc1.bz2;patch=1 \
# git://rsync.kernel.org/pub/scm/linux/kernel/git/torvalds/linux-2.6.git \
# ftp://ftp.kernel.org/pub/linux/kernel/v2.6/testing/patch-2.6.14-rc4.bz2;patch=1 \
# ftp://ftp.kernel.org/pub/linux/kernel/people/akpm/patches/2.6/2.6.14-rc2/2.6.14-rc2-mm1/2.6.14-rc2-mm1.bz2;patch=1 \	   

# Patches submitted upstream are towards top of this list 
# Hacks should clearly named and at the bottom
SRC_URI = "ftp://ftp.kernel.org/pub/linux/kernel/v2.6/linux-2.6.14.tar.gz \
           http://www.kernel.org/pub/linux/kernel/v2.6/snapshots/patch-2.6.14-git3.bz2;patch=1 \
           ${RPSRC}/archive/revert_bootmem-r1.patch;patch=1 \
           ${RPSRC}/archive/pxa_pmops_static-r2.patch;patch=1 \
           ${RPSRC}/archive/pxa_ohci_platform-r2.patch;patch=1 \
           ${RPSRC}/archive/pxa_ohci_suspend-r2.patch;patch=1 \
           ${RPSRC}/archive/akita_mtd_fix-r0.patch;patch=1 \
           ${RPSRC}/archive/spitz_cf-r7.patch;patch=1 \
           ${RPSRC}/archive/sharpsl_pm-r12.patch;patch=1 \
           ${RPSRC}/archive/corgi_pm-r5.patch;patch=1 \
           ${RPSRC}/archive/spitz_pm-r8.patch;patch=1 \
           ${RPSRC}/archive/spitz_base_extras-r12.patch;patch=1 \
           ${RPSRC}/enable_iwmmxt-r0.patch;patch=1 \
           ${RPSRC}/archive/pxa_i2c_fixes-r3.patch;patch=1 \
           ${RPSRC}/ide_not_removable-r0.patch;patch=1 \
           ${RPSRC}/pxa_timerfix-r0.patch;patch=1 \
           ${RPSRC}/archive/pxa_rtc-r1.patch;patch=1 \
           ${RPSRC}/archive/input_power-r2.patch;patch=1 \
           ${RPSRC}/jffs2_longfilename-r0.patch;patch=1 \
           ${RPSRC}/archive/corgi_snd-r14.patch;patch=1 \
           ${RPSRC}/pxa25x_cpufreq-r0.patch;patch=1 \
           ${RPSRC}/archive/fbdev-r0.patch;patch=1 \
           ${RPSRC}/archive/fbdev1-r0.patch;patch=1 \
           ${RPSRC}/archive/fbdev2-r0.patch;patch=1 \
           ${RPSRC}/archive/fbdev3-r0.patch;patch=1 \
           ${RPSRC}/archive/con_rotate-r0.patch;patch=1 \
           ${RPSRC}/archive/con_rotate1-r1.patch;patch=1 \
           ${RPSRC}/archive/hx2750_base-r22.patch;patch=1 \
           ${RPSRC}/archive/hx2750_bl-r3.patch;patch=1 \
           ${RPSRC}/archive/hx2750_pcmcia-r1.patch;patch=1 \
           ${RPSRC}/archive/pxa_keys-r3.patch;patch=1 \
           ${RPSRC}/archive/tsc2101-r9.patch;patch=1 \
           ${RPSRC}/archive/hx2750_test1-r2.patch;patch=1 \
           ${DOSRC}/tc6393-device-r5.patch;patch=1 \
           ${DOSRC}/tc6393_nand-r6.patch;patch=1 \
           ${DOSRC}/tosa-machine-base-r12.patch;patch=1 \
           ${DOSRC}/tosa-keyboard-r6.patch;patch=1 \
           ${DOSRC}/tosa-power-r6.patch;patch=1 \
           ${DOSRC}/tosa-mmc-r5.patch;patch=1 \
           ${DOSRC}/tosa-udc-r4.patch;patch=1 \
           ${DOSRC}/tosa-irda-r3.patch;patch=1 \
           ${DOSRC}/tosa-lcd-r3.patch;patch=1 \
           ${RPSRC}/temp/tosa-bl-r7.patch;patch=1 \
           ${RPSRC}/pcmcia_dev_ids-r2.patch;patch=1 \
           ${RPSRC}/mmc_timeout-r0.patch;patch=1 \	   
           ${RPSRC}/pxa_cf_initorder_hack-r1.patch;patch=1 \
           ${RPSRC}/alsa/alsa_soc_0.5-r0.patch;patch=1 \
           ${RPSRC}/archive/alsa_snd_corgi-r0.patch;patch=1 \
           file://add-oz-release-string.patch;patch=1 \
           file://pxa-serial-hack.patch;patch=1 \
           ${RPSRC}/jl1/pxa-linking-bug.patch;patch=1 \
           file://dtl1_cs-add-socket-revE.patch;patch=1 \
           file://serial-add-support-for-non-standard-xtals-to-16c950-driver.patch;patch=1 \
           file://connectplus-remove-ide-HACK.patch;patch=1 \
           file://defconfig-c7x0 \
           file://defconfig-ipaq-pxa270 \
           file://defconfig-collie \
           file://defconfig-poodle \
           file://defconfig-cxx00 \
           file://defconfig-tosa "
#           file://add-elpp-stuff.patch;patch=1 

# These patches would really help collie/poodle but we 
# need someone to maintain them
# ${JLSRC}/zaurus-lcd-2.6.11.diff.gz;patch=1 
#   (Pavel Machek's git tree has updated versions of this?)
#   Also parts were recently committed to mainline by rmk (drivers/mfd/)
# ${JLSRC}/zaurus-base-2.6.11.diff.gz;patch=1 
#   (This is mostly in mainline now?)
# ${JLSRC}/zaurus-local-2.6.11.diff.gz;patch=1 \
# ${JLSRC}/zaurus-leds-2.6.11.diff.gz;patch=1 \

SRC_URI_append_tosa = "${DOSRC}/nand-readid-r1.patch;patch=1 \
                       ${RPSRC}/temp/tc6393fb-r7.patch;patch=1 \
                       ${DOSRC}/pxa-ac97-suspend-r0.patch;patch=1 \
		       ${DOSRC}/ac97codec-rename-revert-r0.patch;patch=1 \
                       ${DOSRC}/wm9712-ts-r3.patch;patch=1 \
                       ${DOSRC}/tosa-pxaac97-r4.patch;patch=1 \
        	       ${DOSRC}/tosa-bluetooth-r0.patch;patch=1 "

S = "${WORKDIR}/linux-2.6.14"

# to get module dependencies working
KERNEL_RELEASE = "2.6.14-git3"
