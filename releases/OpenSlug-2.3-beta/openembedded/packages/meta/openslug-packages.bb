DESCRIPTION = "Packages that are compatible with the OpenSlug firmware"
LICENSE = MIT
PR = "r3"

INHIBIT_DEFAULT_DEPS = "1"
ALLOW_EMPTY = 1
PACKAGES = "${PN}"

OPENSLUG_DEVELOPMENT = "\
	autoconf \
	automake \
	bash \
	binutils \
	bison \
	bzip2 \
	coreutils \
	cvs \
	diffutils \
	diffstat \
	findutils \
	flex \
	gawk \
	gcc \
	gdb \
	gnu-config \
	grep \
	ipkg-utils \
	lsof \
	m4 \
	make \
	monotone-4 monotone-5 \
	ncurses \
	openssh \
	patch \
	pciutils \
	pkgconfig \
	quilt \
	sed \
	util-linux \
	"

# These packages only build on TARGET_OS=linux, not
# TARGET_OS=linux-uclibc
OPENSLUG_DEVELOPMENT_append_linux = "\
	perl \
	tar \
	"


OPENSLUG_PACKAGES = "\
	bash \
	bluez-utils-nodbus \
	bridge-utils \
	bwmon \
	coreutils \
	cron \
	cvs\
	dnsmasq \
	expat \
	ftpd-topfield \
	glib-2.0 \
	gphoto2 \
	gtk-doc \
	gzip \
	joe \
	less \
	libusb \
	libxml2 \
	miau \ 
	microcom \
	mt-daapd \
	mutt \
	mysql \
	nail \
	openssh \
	openvpn \
	pcre \
	ppp \
	puppy \
	pwc \
	rsync \
	sudo \
	screen \ 
	sysfsutils \
	thttpd \
	timezones \
	db4 \
	openldap \
	openntpd \
	openssh \
	ntp \
	reiserfsprogs reiser4progs \
	procps \
	psmisc \
	python \
	samba \
	sane-backends \
	strace \
	thttpd \
	vlan \
	wakelan \
	wget \
	unionfs-modules unionfs-utils \
	zlib \
	"

# These packages only build on TARGET_OS=linux, not
# TARGET_OS=linux-uclibc  (Note that for several this
# is because of use of single precision FP interfaces
# such as sinf.)
OPENSLUG_PACKAGES_append_linux = "\
	bind \
	mgetty \
	mpd \
	nfs-utils \
	libpam \
	php \
	xinetd \
	yp-tools ypbind ypserv \
	"

BROKEN_PACKAGES = "\
	atftp \
	postfix \
	"

DEPENDS = 'openslug-image \
	${OPENSLUG_PACKAGES} \
	${OPENSLUG_DEVELOPMENT} \
	openslug-native \
	package-index'
