package es.caib.translatorib.core.service;

import es.caib.translatorib.core.api.model.*;
import es.caib.translatorib.core.api.model.comun.Propiedad;
import es.caib.translatorib.core.api.service.TraduccionService;
import es.caib.translatorib.core.service.component.ConfiguracionComponent;
import es.caib.translatorib.core.service.repository.dao.PluginDaoImpl;
import es.caib.translatorib.plugin.api.ITraduccionPlugin;
import es.caib.translatorib.plugin.api.TraduccionException;
import org.fundaciobit.pluginsib.core.IPlugin;
import org.fundaciobit.pluginsib.core.utils.PluginsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.security.PermitAll;
import java.util.Properties;
import java.util.Set;

/**
 * Servei para gestionar la traduccion.
 *
 * @author Indra
 */
@Service
@Transactional
public class TraduccionServiceImpl implements TraduccionService {

	private static final Logger LOG = LoggerFactory.getLogger(TraduccionServiceImpl.class);

	@Autowired
	ConfiguracionComponent configuracionComponent;
    @Autowired
    private PluginDaoImpl pluginDao;

	@Override
	@PermitAll
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
	@PermitAll
	public ResultadoTraduccionDocumento realizarTraduccionDocumento(final String contenidoDocumentoB64,
																	final TipoDocumento tipoDocumento, final Idioma idiomaEntrada, final Idioma idiomaSalidad,
																	final String plugin, final Opciones opciones) {
		final ITraduccionPlugin plg = (ITraduccionPlugin) createPlugin(plugin);
		ResultadoTraduccionDocumento res;
		try {
			res = plg.realizarTraduccionDocumento(contenidoDocumentoB64, tipoDocumento, idiomaEntrada, idiomaSalidad,
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
	 * @param identificador        Identificador del plugin
	 * @return plugin
	 */
	private IPlugin createPlugin(final String identificador) {

		IPlugin plg = null;
		Plugin plugin = null;
		try {
			final Properties prop = new Properties();
			String implementacionPlugin;
			String classname;
			if (identificador == null) {
				plugin = pluginDao.getPluginDefault();
			} else {
				plugin =pluginDao.getPluginByIdentificador(identificador);
			}
			classname = plugin.getClassname();
			if (plugin.getPropiedades() != null) {
				for (final Propiedad key : plugin.getPropiedades()) {
					//prop.put(ITraduccionPlugin.TRANSLATOR_BASE_PROPERTY + key.getCodigo(), key.getValor());
					prop.put(ITraduccionPlugin.TRANSLATOR_BASE_PROPERTY + plugin.getPrefijo() +  key.getCodigo(), key.getValor());
				}
			}

			plg = (IPlugin) PluginsManager.instancePluginByClassName(classname,
					ITraduccionPlugin.TRANSLATOR_BASE_PROPERTY, prop);
		} catch (final Exception e) {
			LOG.error("Error obteniendo el plugin", e);
			if (identificador == null) {
				LOG.error("El identificador es nulo");
			} else if (plugin == null) {
				LOG.error("El jplugin es nulo");
			}  else if (plg == null) {
				LOG.error("El plugin es nulo");
			} else {
				LOG.error("El identificador NO es nulo " + identificador + " ni el plugin ni el jplugin");
			}
		}
		return plg;
	}

	/**
	 * Crea plugin.
	 *
	 * @param plugin        Identificador del plugin
	 * @return plugin
	 * @deprecated Usar createPlugin ya que las opciones están deprecated
	 */
	private IPlugin createPluginPorPropiedades(final String plugin) {

		IPlugin plg = null;
		String classname = null;
		try {
			final Properties prop = new Properties();

			String implementacionPlugin;
			if (plugin == null) {
				implementacionPlugin = configuracionComponent.obtenerPropiedadConfiguracion("es.caib.translatorib.default");
			} else {
				implementacionPlugin = plugin;
			}
			classname = configuracionComponent.obtenerPropiedadConfiguracion("es.caib.translatorib." + implementacionPlugin + ".classname");

			final Set<String> keys = configuracionComponent.getPropertiesNames();
			for (final String key : keys) {
				// Comprobamos si la propiedad hay que cargarla de system
				final String valorProp = configuracionComponent.obtenerPropiedadConfiguracion(key); // replacePlaceholders(propiedadesejb.properties.getProperty(key));
				prop.put(ITraduccionPlugin.TRANSLATOR_BASE_PROPERTY + key, valorProp);
			}
			plg = (IPlugin) PluginsManager.instancePluginByClassName(classname,
					ITraduccionPlugin.TRANSLATOR_BASE_PROPERTY, prop);
		} catch (final Exception e) {
			LOG.error("Error obteniendo el plugin", e);
			if (classname == null) {
				LOG.error("El classname es nulo");
			} else {
				LOG.error("El classname NO es nulo " + classname);
			}
		}
		return plg;
	}
}
