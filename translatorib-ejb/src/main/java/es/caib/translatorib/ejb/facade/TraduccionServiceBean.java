package es.caib.translatorib.ejb.facade;
import es.caib.translatorib.ejb.interceptor.ExceptionTranslate;
import es.caib.translatorib.ejb.interceptor.Logged;
import es.caib.translatorib.ejb.interceptor.NegocioInterceptor;
import es.caib.translatorib.persistence.repository.PluginRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import es.caib.translatorib.service.model.*;
import es.caib.translatorib.service.service.TraduccionService;

import javax.annotation.security.PermitAll;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import es.caib.translatorib.ejb.service.component.ConfiguracionComponent;
import es.caib.translatorib.plugin.api.ITraduccionPlugin;
import es.caib.translatorib.plugin.api.TraduccionException;
import org.fundaciobit.pluginsib.core.IPlugin;
import org.fundaciobit.pluginsib.core.utils.PluginsManager;
import java.util.Properties;

import es.caib.translatorib.service.model.comun.Propiedad;

@Logged
@ExceptionTranslate
@Stateless
@Local(TraduccionService.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class TraduccionServiceBean implements TraduccionService {

	private static final Logger LOG = LoggerFactory.getLogger(TraduccionServiceBean.class);

	@Inject
	ConfiguracionComponent configuracionComponent;

	@Inject
	private PluginRepository pluginDao;

	@Override
	@PermitAll
	@NegocioInterceptor
	public ResultadoTraduccionTexto realizarTraduccion(String textoEntrada, TipoEntrada tipoEntrada, Idioma idiomaEntrada, Idioma idiomaSalidad, String plugin, Opciones opciones) {
		final ITraduccionPlugin plg  = (ITraduccionPlugin) createPlugin(plugin);
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
	@NegocioInterceptor
	public ResultadoTraduccionDocumento realizarTraduccionDocumento(String contenidoDocumentoB64, TipoDocumento tipoDocumento, Idioma idiomaEntrada, Idioma idiomaSalidad, String plugin, Opciones opciones) {
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
			String classname;
			if (identificador == null) {
				plugin = pluginDao.getPluginDefault();
			} else {
				plugin =pluginDao.getPluginByIdentificador(identificador);
			}
			classname = plugin.getClassname();
			if (plugin.getPropiedades() != null) {
				for (final Propiedad key : plugin.getPropiedades()) {
					String valor = configuracionComponent.replacePlaceholders(key.getValor());
					prop.put(ITraduccionPlugin.TRANSLATOR_BASE_PROPERTY + plugin.getPrefijo() +  key.getCodigo(), valor);
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
			}  else {
				LOG.error("El identificador NO es nulo {} {}" , identificador , " ni el plugin ni el jplugin");
			}
		}
		return plg;
	}

}
