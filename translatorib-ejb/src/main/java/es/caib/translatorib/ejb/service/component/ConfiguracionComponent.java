package es.caib.translatorib.ejb.service.component;


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

    /**
     * Leer el valor de una propiedad para properties.
     * @param propiedad La propiedad
     * @return El valor de la propiedad.
     */
    String readPropiedad(String propiedad);

    /**
     * Obtiene el valor reemplazando los placeholders.
     * @param valor Valor
     * @return Valor con placeholders reemplazados
     */
    String replacePlaceholders(String valor);
}