package es.caib.translatorib.core.service.component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import es.caib.translatorib.core.api.exception.ConfiguracionException;
import es.caib.translatorib.core.api.exception.PluginErrorException;

@Component("configuracionComponent")
public class ConfiguracionComponentImpl implements ConfiguracionComponent {

    /**
     * Propiedades configuración especificadas en properties.
     */
    private Properties propiedadesLocales;

    /**
     * Directorio configuración.
     */
    private String directorioConf;

    @PostConstruct
    public void init() {
        final String pathProperties = System
                .getProperty("es.caib.translatorib.properties.path");
        // Carga fichero de propiedades
        try (FileInputStream fis = new FileInputStream(pathProperties);) {
            propiedadesLocales = new Properties();
            propiedadesLocales.load(fis);
        } catch (final IOException e) {
            throw new ConfiguracionException(e);
        }
        // Obtiene directorio configuracion
        final File f = new File(
                System.getProperty("es.caib.translatorib.properties.path"));
        directorioConf = f.getParentFile().getAbsolutePath();
    }

    @Override
    public String obtenerDirectorioConfiguracion() {
        return directorioConf;
    }

    @Override
    public Set<String> getPropertiesNames() {
        return propiedadesLocales.stringPropertyNames();
    }

    @Override
    public String obtenerPropiedadConfiguracion(final String propiedad) {
        return readPropiedad(propiedad);
    }

    // ----------------------------------------------------------------------
    // FUNCIONES PRIVADAS
    // ----------------------------------------------------------------------

    private String createPlugin(String id) {

        return "";
    }

    /**
     * Lee propiedad.
     *
     * @param propiedad
     *            propiedad
     * @return valor propiedad (nulo si no existe)
     */
    private String readPropiedad(final String propiedad) {
        // Busca primero en propiedades locales
        final String prop = propiedadesLocales
                .getProperty(propiedad.toString());
        return prop;
    }

    /**
     * Lee propiedad.
     *
     * @param prefix Prefix
     * @return valor propiedad (nulo si no existe)
     */
    private Map<String, String> readPropiedades(final String prefix) {
        // Busca primero en propiedades locales
        final Map<String, String> props = new HashMap<>();
        for (final Object key : propiedadesLocales.keySet()) {
            if (key.toString().startsWith(prefix)) {
                props.put(key.toString().substring(prefix.length()),
                        propiedadesLocales.getProperty(key.toString()));
            }
        }
        return props;
    }

}
