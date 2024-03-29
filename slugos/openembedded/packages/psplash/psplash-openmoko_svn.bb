require psplash.inc
require psplash-ua.inc

ALTERNATIVE_PRIORITY = "30"

# You can create your own pslash-hand-img.h by doing
# ./make-image-header.sh <file>.png HAND
# and rename the resulting .h to pslash-hand-img.h (for the logo)
# respectively psplash-bar-img.h (BAR) for the bar.
# You might also want to patch the colors (see patch)

SRC_URI = "svn://svn.o-hand.com/repos/misc/trunk;module=psplash;proto=http \
          file://psplash-hand-img.h \
          file://psplash-bar-img.h \
          file://psplash-default \
          file://psplash-init"
S = "${WORKDIR}/psplash"

SRC_URI_append_openmoko = " file://configurability.patch;patch=1 "
