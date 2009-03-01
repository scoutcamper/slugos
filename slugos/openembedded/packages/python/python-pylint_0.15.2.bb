DESCRIPTION = "Pylint is a python tool that checks if a module satisfy a coding standard. Pylint can be seen as another     \
pychecker since nearly all tests you can do with PyChecker can also be done with Pylint. But Pylint offers some more     \
features, like checking line-code's length, checking if variable names are well-formed according to your coding standard,\
or checking if declared interfaces are truly implemented, and much more."
SECTION = "devel/python"
HOMEPAGE = "http://www.logilab.org/857"
PRIORITY = "optional"
LICENSE = "GPL"
RDEPENDS = "python-logilab-common"
SRCNAME = "pylint"
PR = "ml0"

SRC_URI = "ftp://ftp.logilab.org/pub/${SRCNAME}/${SRCNAME}-${PV}.tar.gz"
S = "${WORKDIR}/${SRCNAME}-${PV}"

inherit distutils
