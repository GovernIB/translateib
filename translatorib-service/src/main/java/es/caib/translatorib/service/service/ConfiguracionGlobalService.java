package es.caib.translatorib.service.service;


import es.caib.translatorib.service.model.ConfiguracionGlobal;
import es.caib.translatorib.service.model.filtro.ConfiguracionGlobalFiltro;

import java.util.List;

/**
 * Servicio para configuracion global.
 *
 * @author Indra
 */
public interface ConfiguracionGlobalService {

    /**
     * Actualiza los datos de una configuración global en la base de datos.
     *
     * @param dto nuevos datos de una configuración global
     */
    void updateConfGlobal(ConfiguracionGlobal dto);


    /**
     * Retorna un opcional con la configuración global indicada por el identificador.
     *
     * @param id identificador de la configuración global a buscar
     * @return un opcional con los datos de la configuración global indicada o vacío si no existe
     */
    ConfiguracionGlobal findConfGlobalById(Long id);

    /**
     * Obtiene una configuracion global segun su propiedad
     * @param propiedad Propiedad
     * @return Conf global
     */
    ConfiguracionGlobal findConfGlobalByPropiedad(String propiedad);

    /**
     * Obtiene el valor de una propiedad
     * @param propiedad Propiedad
     * @return El valor de la propiedad
     */
    String valorByPropiedad(String propiedad);

    /**
     * Devuelve una lista con configuraciones relacionados con los parámetros del filtro
     *
     * @param filtro filtro de la búsqueda
     * @return una lista de configuraciones.
     */
    List<ConfiguracionGlobal> listConfGlobalByFiltro(ConfiguracionGlobalFiltro filtro);

    /**
     * Devuelve el número de configuraciones relacionados con los parámetros del filtro
     *
     * @param filtro filtro de la búsqueda
     * @return el número de configuraciones.
     */
    int countConfGlobalByFiltro(ConfiguracionGlobalFiltro filtro);

}
