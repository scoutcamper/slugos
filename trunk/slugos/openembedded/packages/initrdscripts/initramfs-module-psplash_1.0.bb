SRC_URI = "file://00-psplash.sh file://99-psplash.sh"
PR = "r1"
RDEPENDS = "initramfs-uniboot psplash"
DESCRIPTION = "An initramfs module to enable psplash."

do_install() {
    install -d ${D}/initrd.d
    install -m 0755 ${WORKDIR}/00-psplash.sh ${D}/initrd.d/
    install -m 0755 ${WORKDIR}/99-psplash.sh ${D}/initrd.d/
}

PACKAGE_ARCH = "all"
FILES_${PN} += " /initrd.d/* "
