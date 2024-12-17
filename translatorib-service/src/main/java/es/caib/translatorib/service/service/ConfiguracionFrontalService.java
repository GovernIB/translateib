package es.caib.translatorib.service.service;


import es.caib.translatorib.service.model.ConfiguracionFrontal;

/**
 * Servicio para Configuracion frontal.
 *
 * @author Indra
 */
public interface ConfiguracionFrontalService {

    /**
     * Actualiza los datos de una configuración global en la base de datos.
     *
     * @param dto nuevos datos de una configuración global
     */
    void updateConfiguracionFrontal(ConfiguracionFrontal dto);

    /**
     * Retorna un opcional con la configuración global indicada.
     *
     * @return un opcional con los datos de la configuración global indicada o vacío si no existe
     */
    ConfiguracionFrontal findConfFrontalByDefault();

    /**
     * Retorna un opcional con la configuración global indicada por el identificador.
     *
     * @param id identificador de la configuración global a buscar
     * @return un opcional con los datos de la configuración global indicada o vacío si no existe
     */
    ConfiguracionFrontal findConfFrontalById(Long id);


}
