require curl_${PV}.bb
inherit sdk
DEPENDS = "zlib-sdk"

do_stage () {
        install -d ${STAGING_INCDIR}/curl
        install -m 0644 ${S}/include/curl/*.h ${STAGING_INCDIR}/curl/
        oe_libinstall -so -a -C lib libcurl ${STAGING_LIBDIR}
}

do_install() {
	:
}

