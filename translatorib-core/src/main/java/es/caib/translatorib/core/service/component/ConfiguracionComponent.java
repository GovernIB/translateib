package es.caib.translatorib.core.service.component;


import java.util.Set;

/**
 * Componente para acceder a configuracion.
 *
 * @author Indra
 *
 */
public interface ConfiguracionComponent {

    /**
     * Obtiene configuración.
     *
     * @param propiedad
     *            Propiedad configuración
     *
     * @return configuración
     */
    String obtenerPropiedadConfiguracion(String propiedad);


    /**
     * Obtiene directorio de configuración.
     * 
     * @return directorio de configuración
     */
    String obtenerDirectorioConfiguracion();

    /**
     * Devuelve las propiedades por claves
     * @return Lista de propiedades
     */
    Set<String> getPropertiesNames();
}