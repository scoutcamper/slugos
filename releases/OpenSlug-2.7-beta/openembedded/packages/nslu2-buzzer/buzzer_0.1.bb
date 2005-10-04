PR = "r1"
MAINTAINER = "Oyvind Repvik <nail@nslu2-linux.org"
LICENCE = "GPL-2"

SRC_URI = "file://buzzer.c"

S = "${WORKDIR}"
#inherit autotools

do_compile() {
	${CC} -o buzzer buzzer.c
}

do_install() {
	install -d ${D}/usr/bin
	install -m 4755 ${S}/buzzer ${D}/usr/bin
} 
