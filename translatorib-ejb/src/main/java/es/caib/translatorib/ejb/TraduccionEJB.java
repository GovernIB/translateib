package es.caib.translatorib.ejb;

import java.util.Properties;
import java.util.Set;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.fundaciobit.pluginsib.core.IPlugin;
import org.fundaciobit.pluginsib.core.utils.PluginsManager;

import es.caib.translatorib.commons.utils.Constants;
import es.caib.translatorib.ejb.api.model.Idioma;
import es.caib.translatorib.ejb.api.model.Opciones;
import es.caib.translatorib.ejb.api.model.Resultado;
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
@RolesAllowed(Constants.TIB_ADMIN)
public class TraduccionEJB implements TraduccionService {

	@EJB
	PropiedadesTraduccionEJB propiedadesejb;

	@Override
	public Resultado realizarTraduccion(final String textoEntrada, final TipoEntrada tipoEntrada,
			final Idioma idiomaEntrada, final Idioma idiomaSalidad, final Opciones opciones) {
		final ITraduccionPlugin plg = (ITraduccionPlugin) createPlugin(opciones);
		Resultado res;
		try {
			res = plg.realizarTraduccion(textoEntrada, tipoEntrada, idiomaEntrada, idiomaSalidad, opciones);
		} catch (final TraduccionException exp) {
			res = new Resultado();
			res.setError(true);
			res.setDescripcionError(exp.getMessage());
		}
		return res;

	}

	@Override
	public Resultado realizarTraduccionDocumento(final byte[] contenidoDocumento, final TipoDocumento tipoDocumento,
			final Idioma idiomaEntrada, final Idioma idiomaSalidad, final Opciones opciones) {
		final ITraduccionPlugin plg = (ITraduccionPlugin) createPlugin(opciones);
		Resultado res;
		try {
			res = plg.realizarTraduccionDocumento(contenidoDocumento, tipoDocumento, idiomaEntrada, idiomaSalidad,
					opciones);
		} catch (final TraduccionException exp) {
			res = new Resultado();
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
	private IPlugin createPlugin(final Opciones opciones) {

		IPlugin plg = null;
		try {
			final Properties prop = new Properties();
			final Properties properties = propiedadesejb.getProperties();
			String classname;

			if (opciones == null || !opciones.contains(Opciones.PLUGIN)) {
				classname = properties.getProperty("translatorib.default");
			} else {
				classname = opciones.getValor(Opciones.PLUGIN);
			}

			final Set<String> keys = properties.stringPropertyNames();
			for (final String key : keys) {
				// Comprobamos si la propiedad hay que cargarla de system
				final String valorProp = properties.getProperty(key); // replacePlaceholders(propiedadesejb.properties.getProperty(key));
				prop.put(ITraduccionPlugin.TRANSLATOR_BASE_PROPERTY + key, valorProp);
			}
			plg = (IPlugin) PluginsManager.instancePluginByClassName(classname,
					ITraduccionPlugin.TRANSLATOR_BASE_PROPERTY, prop);
		} catch (final Exception e) {
			System.out.println("Error " + e.getMessage());
		}
		return plg;
	}

}
