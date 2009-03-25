DESCRIPTION = "Qi bootloader w/ Openmoko GTA01, GTA02, and GTA03 support"
LICENSE = "GPL"
SECTION = "bootloader"
PROVIDES = "virtual/bootloader"
LOCALVERSION = "gitr${SRCREV}"
PV = "1.0.0+${LOCALVERSION}"

EXTRA_OEMAKE = ""
PARALLEL_MAKE = ""

do_configure() {
	echo >config.mk
	# uncomment to debug
#	sed -i s,@,, Makefile
	cat >config.mk <<EOF

BUILD_HOST=\$(shell hostname)
BUILD_VERSION=${PV}
BUILD_DATE=\$(shell date)

GCCV="`${CC} -dumpversion`"
COMPILER_LIB_PATH_PRE=${CROSS_DIR}/lib/gcc/${TARGET_SYS}/$(GCCV)
COMPILER_LIB_PATH=\$(COMPILER_LIB_PATH_PRE)/\$(shell ls \$(COMPILER_LIB_PATH_PRE))

AS=${AS}
LD=${LD}
CC=${CC}
OBJCOPY=${OBJCOPY}
OBJDUMP=${OBJDUMP}
HOSTCC=${BUILD_CC}

export HOSTCC BUILD_HOST BUILD_VERSION BUILD_DATE CROSS_COMPILE AD LD CC OBJCOPY OBJDUMP MKUDFU
EOF	
}

do_compile() {
	make clean && \
	make CPU=s3c2442 && \
	make CPU=s3c2410 && \
	make CPU=s3c6410
}

do_deploy () {
	install -d ${DEPLOY_DIR_IMAGE}
	cp -f image/qi-*.udfu ${DEPLOY_DIR_IMAGE}
}

PACKAGE_ARCH = "${MACHINE_ARCH}"

do_deploy[dirs] = "${S}"
addtask deploy before do_package after do_install
addtask quilt before do_patch after do_unpack
addtask svnrev before do_patch after do_quilt