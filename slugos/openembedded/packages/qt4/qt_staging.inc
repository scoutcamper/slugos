# Fixing up some Qt .pc files
do_stage_append() {
    cd ${STAGING_LIBDIR}/pkgconfig
    sed -i s#"-L${S}/lib"##g Qt*.pc
    sed -i s#"moc_location=${STAGING_BINDIR}/moc4$"## Qt*.pc
    sed -i s#"uic_location=${STAGING_BINDIR}/uic4$"## Qt*.pc
    sed -i s#"-L${STAGING_LIBDIR}"##g Qt*.pc
    sed -i s#'$(OE_QMAKE_LIBS_X11)'#"${OE_QMAKE_LIBS_X11}"#g Qt*.pc
	sed -i s#"-L${S}/lib"##g phonon*.pc
	sed -i s#"moc_location=${STAGING_BINDIR}/moc4$"## phonon*.pc
    sed -i s#"uic_location=${STAGING_BINDIR}/uic4$"## phonon*.pc
    sed -i s#"-L${STAGING_LIBDIR}"##g phonon*.pc
    sed -i s#'$(OE_QMAKE_LIBS_X11)'#"${OE_QMAKE_LIBS_X11}"#g phonon*.pc

    cd ${STAGING_LIBDIR}
    sed -i s#"-L${S}/lib"##g libQt*.la
    sed -i s#'$(OE_QMAKE_LIBS_X11)'#"${OE_QMAKE_LIBS_X11}"#g libQt*.la

    install -d ${STAGING_DIR_TARGET}/qt4/bin
    cp ${STAGING_DIR_NATIVE}/qt4/bin/* ${STAGING_DIR_TARGET}/qt4/bin/ || true 
    cp ${STAGING_DIR_NATIVE}/${bindir}/{rcc*,uic*,moc*} ${STAGING_DIR_TARGET}/qt4/bin/ || true	
    for qttool in ${STAGING_DIR_TARGET}/qt4/bin/*4 ; do
         ln -sf $qttool  ${STAGING_DIR_TARGET}/qt4/bin/$(basename $qttool |sed s:4::g) 
    done
}

STAGE_TEMP = "${WORKDIR}/temp-staging"
do_stage() {
    rm -rf ${STAGE_TEMP}
    mkdir -p ${STAGE_TEMP}
    oe_runmake install INSTALL_ROOT=${STAGE_TEMP}

    install -d ${STAGING_INCDIR}/
    install -d ${STAGING_LIBDIR}
    cp -pPRf ${STAGE_TEMP}/$includedir/* ${STAGING_INCDIR}/

    for i in ${STAGE_TEMP}/${libdir}/*.la
    do
        sed -i s,installed=yes,installed=no, $i
        sed -i -e "/^dependency_libs=/s,-L${libdir},-L${STAGING_LIBDIR},g" $i
        sed -i -e "s,-L${libdir},-L${STAGING_LIBDIR},g" ${STAGE_TEMP}/${libdir}/$(basename $i .la).prl
        cp -fpPR $i ${STAGING_LIBDIR}
        cp -fpPR ${STAGE_TEMP}/${libdir}/$(basename $i .la).prl ${STAGING_LIBDIR}
        oe_libinstall -C ${STAGE_TEMP}/${libdir} -so $(basename $i .la) ${STAGING_LIBDIR}
    done

    for i in libQtAssistantClient${QT_LIBINFIX} libQtDesignerComponents${QT_LIBINFIX} libQtUiTools${QT_LIBINFIX} libQtDesigner${QT_LIBINFIX}
    do
        sed -i -e "s,-L${libdir},-L${STAGING_LIBDIR},g" $i.prl || true
        cp ${STAGE_TEMP}/${libdir}/$i.prl ${STAGING_LIBDIR} || true
        sed -i -e "/^dependency_libs=/s,-L${libdir},-L${STAGING_LIBDIR},g" $i.la || true
        cp ${STAGE_TEMP}/${libdir}/$i.la  ${STAGING_LIBDIR} || true
        oe_libinstall -C ${STAGE_TEMP}/${libdir} -so $i ${STAGING_LIBDIR} || true
        oe_libinstall -C ${STAGE_TEMP}/${libdir} -a $i ${STAGING_LIBDIR} || true
    done

    rm -rf ${STAGE_TEMP}
}
