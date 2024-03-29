#!/bin/bash

# Split into "noarch" "arch" and "machine" section
# build "noarch" for only one machine, build "arch" *one* time for each arch and build "machine" for each machine

DO_UCLIBC=0

do_build() {
        #echo "MACHINE = \"$BUILD_MACHINE\"" > conf/auto.conf

        BUILD_MODE="glibc"
        if [ "$BUILD_CLEAN" != "" ]
        then
                MACHINE=$BUILD_MACHINE bitbake -c clean $BUILD_CLEAN
        fi

        for target in $BUILD_TARGETS
        do
                MACHINE=$BUILD_MACHINE bitbake $target && do_report_success
        done

        if [ $DO_UCLIBC = 1 ]
        then
                BUILD_MODE="uclibc"
                
                if [ "$BUILD_CLEAN" != "" ]
                then
                        ANGSTROMLIBC=uclibc MACHINE=$BUILD_MACHINE bitbake -c clean $BUILD_CLEAN
                fi
                
                for target in $BUILD_TARGETS
                do
                        ANGSTROMLIBC=uclibc MACHINE=$BUILD_MACHINE bitbake $target && do_report_success
                done
        fi
}

do_report_success() {

        echo "$(date -u +%s) $target $BUILD_MODE $machine" >> autobuilder-feed.log
}

# noarch packages (e.g. PACKAGE_ARCH=all in OE)

BUILD_MACHINE="beagleboard"
BUILD_CLEAN=""
BUILD_TARGETS=""
do_build

# arch packages (no machine specific (sub)packages)

# Architectures:
# * arm-oabi:  simpad
# * armv4t:    om-gta01
# * armv5te:   c7x0
# * armv5teb:  ixp4xxbe
# * armv6:     nokia800
# * armv6-novfp: htckaiser
# * armv7a:    beagleboard
# * ppc405:    dht-walnut
# * ppc603e:   efika
# * i586:      qemux86

for machine in simpad om-gta01 c7x0 ixp4xxbe nokia800 htckaiser beagleboard dht-walnut efika qemux86
do
        BUILD_MACHINE=$machine
        BUILD_CLEAN="opkg-native qmake-native qmake2-native qt-x11-free python python-native python-pygtk gnome-icon-theme"
        BUILD_TARGETS=" \
                      abiword \
                      aircrack-ng \
                      angstrom-zeroconf-audio \
                      apache2 \
                      artoolkit \
                      asterisk \
                      atomic \
                      autoconf \
                      automake \
                      binutils \
                      bison \
                      bluez-gnome \
                      bluez-hcidump \
                      boa \
                      bonjour \
                      camera-assistant \
                      cdparanoia \
                      cdstatus \
                      checkers \
                      cherokee \
                      claws-mail \
                      connman \
                      connman-gnome \
                      contacts \
                      cvs \
                      cwiid \
                      dates \
                      distcc \
                      dsniff \
                      duke3d \
                      e-uae \
                      e-wm \
                      ekiga \
                      empathy \ 
                      epdfview \
                      epiphany \
                      evince \
                      exo \
                      fakeroot \
                      fennec \
                      flame \
                      ffmpeg \
                      fftw \
                      fftwf \
                      fftwl \
                      findutils \
                      firefox \
                      fish \
                      flex \
                      flite \
                      frameworkd \
                      freenote \
                      froot \
                      frozen-bubble \
                      gcc \
                      gdal \
                      gdb \
                      gdbserver \
                      gdm \
                      geda \
                      geda-docs \
                      geda-examples \
                      geda-gattrib \
                      geda-gnetlist \
                      geda-gschem \
                      geda-gsymcheck \
                      geda-symbols \
                      geda-utils \
                      gimp \
                      glider \
                      gmp \
                      gmpc \
                      gnome-games \
                      gnome-mplayer \
                      gnome-panel \
                      gnome-system-monitor \
                      gnumeric \
                      gnuplot \
                      gnuradio \
                      gpe-bluetooth \
                      gpe-gallery \
                      gpe-login \
                      gpe-mini-browser \
                      gpe-mini-browser2 \
                      gpe-scap \
                      gpe-sketchbook \
                      git \
                      gperf \
                      gphoto2 \
                      gpsdrive \
                      gqview \
                      gsm0710muxd \
                      gspcav1 \
                      gtk+ \
                      gzip \
                      hal \
                      hexatrolic \
                      i2c-tools \
                      iaimaster \
                      icebloxx \
                      imagemagick \
                      inkwp \
                      ioquake3 \
                      ipaq-sleep \
                      iperf \
                      iptables \
                      irssi \
                      iscsi-target \
                      jamvm \
                      kismet \
                      konqueror-embedded \
                      labyrinth \
                      lcd4linux \
                      leafpad \
                      lighttpd \
                      links-x11 \
                      lirc \
                      lowpan-utils \
                      m4 \
                      madplay \
                      mahjongg \
                      make \
                      maki \
                      mc \
                      mediatomb \
                      meta-nas-server \
                      meta-toolchain \
                      metacity \
                      midori \
                      mileage \
                      mono \
                      motion \
                      mousepad \
                      mpc \
                      mpd \
                      mpfr \
                      mpg123 \
                      mplayer \
                      mtd-utils \
                      mutt \
                      mythtv \
                      nautilus \
                      navit \
                      netsurf \
                      news \
                      nmap \
                      nmm \
                      notecase \
                      numptyphysics \
                      octave \
                      opencv \
                      opencv-samples \
                      openmoko-browser2 \
                      openredalert \
                      openvpn \
                      opie-notes \
                      orage \
                      palantir \
                      pairs \
                      pciutils \
                      pdamaze \
                      perl \
                      pidgin \
                      pine \
                      pingus \
                      pipeman \
                      pkgconfig \
                      places \
                      pocketcellar \
                      povray \
                      prboom \
                      prelink \
                      pushover \
                      python \
                      python-pybluez \
                      python-coherence \
                      qfish2 \
                      qmatrix \
                      qpe-nmap \
                      qt-x11-free \
                      qt4-x11-free \
                      quake2 \
                      quasar \
                      quetoo \
                      rain \
                      rdesktop \
                      read-edid \
                      resistorui \
                      roadmap-gtk2 \
                      rtorrent \
                      ruby \
                      samba \
                      screen \
                      scummvm \
                      sdlquake \
                      sensors-applet \
                      setserial \
                      shisensho \
                      slcalc \
                      sliderulez \
                      sokoban \
                      squeakvm \
                      stalonetray \
                      strace \
                      subversion \
                      sugar \
                      sylpheed \
                      task-gpe-pim \
                      task-openmoko-feed \
                      task-openmoko-games \
                      task-opie-apps \
                      task-opie-games \
                      task-proper-tools \
                      task-xqtlauncher \
                      tasks \
                      texinfo \
                      thttpd \
                      thunar \
                      tightvnc \
                      timesleuth \
                      tofrodos \
                      totem \
                      transmission \
                      tron \
                      tzdata \
                      ubahnnav \
                      ufraw \
                      usbutils \
                      usbview \
                      ushare \
                      viking \
                      vlc \
                      vpnc \
                      w3cat \
                      wifi-radar \
                      win4 \
                      wireshark \
                      wpa-gui \
                      wt \
                      x11vnc \
                      xf86-input-evdev \
                      xf86-input-keyboard \
                      xf86-input-mouse \
                      xf86-input-tslib \
                      xf86-video-ati \
                      xf86-video-fbdev \
                      xf86-video-vesa \
                      xfce-mcs-manager
                      xfce-mcs-manager \
                      xfce-mcs-plugins \
                      xfce-terminal \
                      xfce-utils \
                      xfce4-appfinder \
                      xfce4-dev-tools \
                      xfce4-icon-theme \
                      xfce4-mixer \
                      xfce4-panel \
                      xfce4-session \
                      xfdesktop \
                      xfprint \
                      xfwm4 \
                      xfwm4-themes \
                      xmms \
                      xserver-kdrive \
                      xserver-xorg \
                      zauralign \
                      zddice \
                      zgscore \
                      zhone \
                      ziq \
                      zlapspeed \
                      zrev7 \
                      zsubhunt \
                      ztappy \
                      zuc \
                      zudoku \
                      "
        do_build
done        

# machine packages (machine specific (sub)packages)

for machine in overo omap3-pandora beagleboard omap3evm neuros-osd2 efika dht-walnut palmt650 omap5912osk ixp4xxle ixp4xxbe c7x0 poodle tosa akita spitz collie simpad om-gta01 om-gta02 a780 at91sam9263ek qemuarm h2200 h3900 h4000 hx4700 nokia800 dns323 mv2120 kuropro lspro tsx09 ts409 qemux86  

do
        BUILD_MACHINE=$machine
            BUILD_CLEAN="opkg-native qmake-native qmake2-native qt-x11-free python python-native python-pygtk gnome-icon-theme"
        BUILD_TARGETS="task-base task-boot \
                       task-opie task-opie-all \
                       task-openmoko-base task-openmoko-debug task-openmoko-examples task-openmoko-linux task-openmoko-native-sdk task-openmoko-net task-openmoko-phone task-openmoko-pim task-openmoko-ui \
                       angstrom-x11-base-depends angstrom-gpe-task-settings \
                       xserver-xorg-conf \ 
                       "

        do_build
done

for machine in collie h2200 hx4700 spitz akita tosa poodle c7x0
do
        BUILD_MACHINE=$machine
        BUILD_TARGETS="linux-kexecboot \
		       "
	do_build
done	
