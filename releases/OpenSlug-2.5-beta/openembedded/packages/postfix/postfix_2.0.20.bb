SECTION = "console/network"
DEPENDS = "db3 pcre postfix-native"
LICENSE = "IPL"
PR = "r1"

SRC_URI = "ftp://ftp.porcupine.org/mirrors/postfix-release/official/postfix-${PV}.tar.gz \
	   file://${FILESDIR}/makedefs.patch;patch=1 \
	   file://${FILESDIR}/install.patch;patch=1 \
	   file://main.cf_2.0 \
	   file://volatiles \
	   file://postfix \
	   "

S = "${WORKDIR}/postfix-${PV}"

inherit update-rc.d

INITSCRIPT_NAME = "postfix"
INITSCRIPT_PARAMS = "start 58 3 4 5 . stop 13 0 1 6 ."

export SYSLIBS = "-lpcre -ldb -lnsl -lresolv ${LDFLAGS}"
export EXPORT = "AUXLIBS='-lpcre' CCARGS='-DHAS_PCRE ${CFLAGS}' OPT='' DEBUG='-g'"
export CC_append = " -DHAS_PCRE ${CFLAGS}"
export EXTRA_OEMAKE = "-e"
export POSTCONF = "${STAGING_BINDIR}/postconf"

do_compile () {
	unset CFLAGS CPPFLAGS CXXFLAGS
	oe_runmake makefiles
	oe_runmake
}

do_install () {
	sh ./postfix-install 'install_root=${D}' -non-interactive
	rm -rf ${D}/var/spool/postfix
        mv ${D}${sysconfdir}/postfix/main.cf ${D}${sysconfdir}/postfix/sample-main.cf
	install -m 644 ${WORKDIR}/main.cf_2.0 ${D}${sysconfdir}/postfix/main.cf
        install -m 644 ${WORKDIR}/volatiles ${D}${sysconfdir}/default/volatiles/01_postfix
        install -m 755 ${WORKDIR}/postfix ${D}${sysconfdir}/init.d/postfix
}

pkg_postinst () {
        grep postfix /etc/group || addgroup postfix
        grep postdrop /etc/group || addgroup postdrop
        grep vmail /etc/group || addgroup vmail
        grep postfix /etc/passwd || adduser --disabled-password --home=/var/spool/postfix --ingroup postfix postfix
        grep vmail /etc/passwd || adduser --disabled-password --home=/var/spool/vmail --ingroup vmail vmail
	/etc/init.d/populate-volatile.sh
}
