require connman.inc
PR = "r4"

EXTRA_OECONF += "\
  --disable-gtk-doc \
  --enable-debug \
  --enable-threads \
  --enable-loopback \
  --enable-ethernet \
  --enable-wifi \
  --disable-wimax \
  --enable-bluetooth \
  --enable-udhcp \
  --enable-dhclient \
  --enable-resolvconf \
  --enable-dnsproxy \
  --disable-novatel \
  --disable-huawei \
  --disable-hso \
  --enable-ppp \
# needs udev >= 129
  --disable-udev \
  --disable-polkit \
  --enable-client \
  --enable-fake \
#  --with-udhcpc=PROGRAM \
#  --with-dhclient=PROGRAM \
#  --with-resolvconf=PROGRAM \
#  --with-pppd=PROGRAM \
"

SRC_URI  = "\
  ftp://ftp.moblin.org/connman/releases/connman-${PV}.tar.gz \
  file://connman \
"

