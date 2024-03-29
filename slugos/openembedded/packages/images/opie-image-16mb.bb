IMAGE_LINGUAS = ""

DEPENDS = "task-boot task-opie-16mb"

ANGSTROM_EXTRA_INSTALL ?= ""
IMAGE_INSTALL = "task-boot \
                    ipkg ipkg-collateral \
                    dropbear \
                    task-opie-16mb-base \
                    task-opie-16mb-applets \
                    task-opie-16mb-inputmethods \
                    task-opie-16mb-settings \
                    task-opie-16mb-apps \
                    task-opie-16mb-pim \
		    task-opie-irda \
                    ${ANGSTROM_EXTRA_INSTALL} "

# create /etc/timestamp from build date
IMAGE_PREPROCESS_COMMAND = "create_etc_timestamp"

inherit image
