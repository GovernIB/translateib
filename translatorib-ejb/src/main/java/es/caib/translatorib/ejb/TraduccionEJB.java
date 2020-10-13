package es.caib.translatorib.ejb;

import java.util.Properties;
import java.util.Set;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.fundaciobit.pluginsib.core.IPlugin;
import org.fundaciobit.pluginsib.core.utils.PluginsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.translatorib.commons.utils.Constants;
import es.caib.translatorib.ejb.api.model.Idioma;
import es.caib.translatorib.ejb.api.model.Opciones;
import es.caib.translatorib.ejb.api.model.ResultadoTraduccionDocumento;
import es.caib.translatorib.ejb.api.model.ResultadoTraduccionTexto;
import es.caib.translatorib.ejb.api.model.TipoDocumento;
import es.caib.translatorib.ejb.api.model.TipoEntrada;
import es.caib.translatorib.ejb.interceptor.Logged;
import es.caib.translatorib.plugin.api.ITraduccionPlugin;
import es.caib.translatorib.plugin.api.TraduccionException;

/**
 * Servei EJB per gestionar {@link Traduccion}. Li aplicam l'interceptor
 * {@link Logged}, per tant, totes les cridades es loguejeran.
 *
 * @author Indra
 */
@Logged
@Stateless
@RolesAllowed(Constants.TIB_API)
public class TraduccionEJB implements TraduccionService {

	private static final Logger LOG = LoggerFactory.getLogger(TraduccionEJB.class);

	@EJB
	PropiedadesTraduccionEJB propiedadesejb;

	@Override
	public ResultadoTraduccionTexto realizarTraduccion(final String textoEntrada, final TipoEntrada tipoEntrada,
			final Idioma idiomaEntrada, final Idioma idiomaSalidad, final String plugin, final Opciones opciones) {
		final ITraduccionPlugin plg = (ITraduccionPlugin) createPlugin(plugin);
		ResultadoTraduccionTexto res;
		try {
			res = plg.realizarTraduccion(textoEntrada, tipoEntrada, idiomaEntrada, idiomaSalidad, opciones);
		} catch (final TraduccionException exp) {
			LOG.error("Error realizando realizarTraduccion", exp);
			res = new ResultadoTraduccionTexto();
			res.setError(true);
			res.setDescripcionError(exp.getMessage());
		}
		return res;

	}

	@Override
	public ResultadoTraduccionDocumento realizarTraduccionDocumento(final byte[] contenidoDocumento,
			final TipoDocumento tipoDocumento, final Idioma idiomaEntrada, final Idioma idiomaSalidad,
			final String plugin, final Opciones opciones) {
		final ITraduccionPlugin plg = (ITraduccionPlugin) createPlugin(plugin);
		ResultadoTraduccionDocumento res;
		try {
			res = plg.realizarTraduccionDocumento(contenidoDocumento, tipoDocumento, idiomaEntrada, idiomaSalidad,
					opciones);
		} catch (final TraduccionException exp) {
			LOG.error("Error realizando realizarTraduccionDocumento", exp);
			res = new ResultadoTraduccionDocumento();
			res.setError(true);
			res.setDescripcionError(exp.getMessage());
		}
		return res;
	}

	// ----------------------------------------------------------------------
	// FUNCIONES PRIVADAS
	// ----------------------------------------------------------------------

	/**
	 * Crea plugin.
	 *
	 * @param plugins         Lista configuración plugins
	 * @param plgTipo         Tipo plugin
	 * @param lanzarExcepcion Si lanza excepción
	 * @return plugin
	 */
	private IPlugin createPlugin(final String plugin) {

		IPlugin plg = null;
		Properties properties = null;
		String classname = null;
		try {
			final Properties prop = new Properties();
			properties = propiedadesejb.getProperties();

			String implementacionPlugin;
			if (plugin == null) {
				implementacionPlugin = properties.getProperty("default");

			} else {
				implementacionPlugin = plugin;
			}
			classname = properties.getProperty("traductor." + implementacionPlugin + ".classname");

			final Set<String> keys = properties.stringPropertyNames();
			for (final String key : keys) {
				// Comprobamos si la propiedad hay que cargarla de system
				final String valorProp = properties.getProperty(key); // replacePlaceholders(propiedadesejb.properties.getProperty(key));
				prop.put(ITraduccionPlugin.TRANSLATOR_BASE_PROPERTY + key, valorProp);
			}
			plg = (IPlugin) PluginsManager.instancePluginByClassName(classname,
					ITraduccionPlugin.TRANSLATOR_BASE_PROPERTY, prop);
		} catch (final Exception e) {
			LOG.error("Error obteniendo el plugin", e);
			if (properties == null) {
				LOG.error("El fichero properties es nulo");
			}
			if (classname == null) {
				LOG.error("El classname es nulo");
			} else {
				LOG.error("El classname NO es nulo " + classname);
			}
		}
		return plg;
	}

}
