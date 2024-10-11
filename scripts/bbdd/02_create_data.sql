INSERT INTO  TIB_PLUGIN (  PLG_CODIGO, PLG_IDENTI, PLG_DESCR, PLG_CLASS, PLG_PREFIJO, PLG_PROPS, PLG_PORDEF ) VALUES ( TIB_PLUGIN_SEQ.NEXTVAL, 'MOCKUP', 'Mockup', 'es.caib.translatorib.plugin.api.mock.MockTradPlugin', 'es.caib.translatorib.mock', null, 1);
INSERT INTO  TIB_PLUGIN (  PLG_CODIGO, PLG_IDENTI, PLG_DESCR, PLG_CLASS, PLG_PREFIJO, PLG_PROPS, PLG_PORDEF ) VALUES ( TIB_PLUGIN_SEQ.NEXTVAL, 'PLATA', 'Plugin Plata', 'es.caib.translatorib.plugin.api.plata.PlataPlugin', 'es.caib.translatorib.plata', '[{"codigo":"url","valor":"http://pre-apertium.redsara.es:8083/TranslatorService_v2/Translator_v2","orden":null},{"codigo":"user","valor":"govbalearusr","orden":null},{"codigo":"pass","valor":"XXX","orden":null},{"codigo":"timeout","valor":"30","orden":null}]', 0);
INSERT INTO  TIB_PLUGIN (  PLG_CODIGO, PLG_IDENTI, PLG_DESCR, PLG_CLASS, PLG_PREFIJO, PLG_PROPS, PLG_PORDEF ) VALUES ( TIB_PLUGIN_SEQ.NEXTVAL, 'OPENTRAD', 'Plugin Opentrad', 'es.caib.translatorib.plugin.api.opentrad.OpenTradPlugin', 'es.caib.translatorib.opentrad', '[{"codigo":"url","valor":"http://traductorbal.imaxin.com/TranslatorService_v2/Translator_v2","orden":null},{"codigo":"user","valor":"$rolsac_opentrad","orden":null},{"codigo":"pass","valor":"XXX","orden":null},{"codigo":"timeout","valor":"30","orden":null}]', 0);

INSERT INTO TIB_CNFGLO(CFG_CODIGO, CFG_PROP,CFG_VALOR,CFG_DESCR,CFG_NOMOD) VALUES (TIB_CNFGLO_SEQ.NEXTVAL, 'idiomaDefecto', 'ca','En el caso de los tipos que no cuelgan de entidad, en caso de no pasarse el idioma en el restapi, para saber que idioma coger.',0);

INSERT INTO TIB_CNFGLO(CFG_CODIGO, CFG_PROP,CFG_VALOR,CFG_DESCR,CFG_NOMOD) VALUES (TIB_CNFGLO_SEQ.NEXTVAL, 'frontal.restapi.url', 'http://www.caib.es/translatorib/api/services/traduccion/v1/texto','Cuando se conecta desde el frontal, la url de texto.',0);
INSERT INTO TIB_CNFGLO(CFG_CODIGO, CFG_PROP,CFG_VALOR,CFG_DESCR,CFG_NOMOD) VALUES (TIB_CNFGLO_SEQ.NEXTVAL, 'frontal.restapi.url.doc', 'http://www.caib.es/translatorib/api/services/traduccion/v1/documento','Cuando se conecta desde el frontal, la url de doc.',0);
INSERT INTO TIB_CNFGLO(CFG_CODIGO, CFG_PROP,CFG_VALOR,CFG_DESCR,CFG_NOMOD) VALUES (TIB_CNFGLO_SEQ.NEXTVAL, 'frontal.restapi.usr', 'api-tib','Cuando se conecta desde el frontal, el usuario.',0);
INSERT INTO TIB_CNFGLO(CFG_CODIGO, CFG_PROP,CFG_VALOR,CFG_DESCR,CFG_NOMOD) VALUES (TIB_CNFGLO_SEQ.NEXTVAL, 'frontal.restapi.pwd', 'XXXX','Cuando se conecta desde el frontal, el pwd.',0);

INSERT INTO TIB_CNFFRO (CFF_CODIGO, CFF_PLUGIN, CFF_IDIORI, CFF_IDIDES, CFF_PLGSOP)
VALUES (
           TIB_CNFFRO_SEQ.nextval, NULL, NULL, NULL,
           (select wm_concat(PLG_CODIGO) from TIB_PLUGIN)  -- CFF_PLGSOP
    );