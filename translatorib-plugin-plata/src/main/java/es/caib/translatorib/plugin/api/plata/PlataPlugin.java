package es.caib.translatorib.plugin.api.plata;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Properties;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.translatorib.ejb.api.model.Idioma;
import es.caib.translatorib.ejb.api.model.Opciones;
import es.caib.translatorib.ejb.api.model.ResultadoTraduccionDocumento;
import es.caib.translatorib.ejb.api.model.ResultadoTraduccionTexto;
import es.caib.translatorib.ejb.api.model.TipoDocumento;
import es.caib.translatorib.ejb.api.model.TipoEntrada;
import es.caib.translatorib.plata.rest.cxf.CustomFileResponse;
import es.caib.translatorib.plata.rest.cxf.TranslatorV2;
import es.caib.translatorib.plata.rest.cxf.TranslatorV2Service;
import es.caib.translatorib.plugin.api.ITraduccionPlugin;
import es.caib.translatorib.plugin.api.TraduccionException;

/**
 * Interfaz opentrad
 *
 * @author Indra
 *
 */
public class PlataPlugin extends AbstractPluginProperties implements ITraduccionPlugin {

	private static final Logger LOG = LoggerFactory.getLogger(PlataPlugin.class);

	/** Prefix. */
	public static final String IMPLEMENTATION_BASE_PROPERTY = "es.caib.translatorib.plata.";

	public PlataPlugin(final String prefijoPropiedades, final Properties properties) {
		super(prefijoPropiedades, properties);
	}

	@Override
	public ResultadoTraduccionTexto realizarTraduccion(final String codeTranslate, final TipoEntrada tipoEntrada,
			final Idioma idiomaEntrada, final Idioma idiomaSalida, final Opciones opciones) throws TraduccionException {
		final ResultadoTraduccionTexto resultado = new ResultadoTraduccionTexto();
		final String url = getPropiedad("url");
		final String userName = getPropiedad("user");

		final String userPass = getPropiedad("pass");
		final Long timeout = Long.valueOf(getPropiedad("timeout"));

		try {

			final TranslatorV2 port = getClientePlata(url, timeout);

			final java.lang.Boolean proxyCache = false;
			final java.lang.String translationEngine = "plata";
			final String documentBase64 = "";
			final java.lang.String languagePair = getIdioma(idiomaEntrada, idiomaSalida);

			final java.lang.Boolean ner = getNer(opciones);
			final java.lang.String markUnknown = getMarkunkonw(opciones);
			final java.lang.String checksum = "";
			final java.lang.String urlX = "";
			final java.lang.String dirbase = "";
			final java.lang.String contentType;
			if (tipoEntrada == TipoEntrada.XML) {
				contentType = "WXML";
			} else {
				contentType = tipoEntrada.toString();
			}
			final String textoResultado = port.translateString(proxyCache, translationEngine, null, languagePair, ner,
					contentType, markUnknown, codeTranslate, null, null, null, userName, userPass);
			resultado.setError(false);
			resultado.setTextoTraducido(textoResultado);

		} catch (final Exception e) {
			LOG.error("Error realizando la traduccion", e);
			resultado.setError(true);
			resultado.setDescripcionError(ExceptionUtils.getMessage(e));

		}

		return resultado;
	}

	@Override
	public ResultadoTraduccionDocumento realizarTraduccionDocumento(final String documentoEntradaB64,
			final TipoDocumento tipoDocumento, final Idioma idiomaEntrada, final Idioma idiomaSalida,
			final Opciones opciones) throws TraduccionException {
		final ResultadoTraduccionDocumento resultado = new ResultadoTraduccionDocumento();
		final String url = getPropiedad("url");
		final String user = getPropiedad("user");
		final String pass = getPropiedad("pass");
		final Long timeout = Long.valueOf(getPropiedad("timeout"));

		try {

			final TranslatorV2 port = getClientePlata(url, timeout);

			final java.lang.Boolean proxyCache = false;
			final java.lang.String translationEngine = "plata";
			final java.lang.String languagePair = getIdioma(idiomaEntrada, idiomaSalida);

			final java.lang.Boolean ner = getNer(opciones);

			final java.lang.String markUnknown = getMarkunkonw(opciones);
			final java.lang.String urlX = null;
			final java.lang.String dirbase = null;
			final java.lang.String tipoDocumentoStr = getTipoDocumento(tipoDocumento);

			/*** IMPORTANTE, EL CHECKSUM EN MINUSCULAS ***/

			final byte[] targetArray = Base64.getDecoder().decode(documentoEntradaB64);
			final String valorChecksum = (new HexBinaryAdapter())
					.marshal(MessageDigest.getInstance("MD5").digest(targetArray)).toLowerCase();
			final CustomFileResponse textoResultado = port.translateFile(proxyCache, translationEngine,
					documentoEntradaB64, languagePair, ner, tipoDocumentoStr, markUnknown, null, valorChecksum, urlX,
					dirbase, user, pass);
			resultado.setError(false);
			final String textoTrad = textoResultado.getDocumentBase64().replaceAll("\n", "");
			resultado.setTextoTraducido(textoTrad);
			resultado.setDireccion(textoResultado.getDirectionOfTranslate());
			resultado.setChecksum(textoResultado.getDocumentChecksum());
			resultado.setTipo(textoResultado.getDocumentType());
			resultado.setDescripcionError(textoResultado.getError());

		} catch (final Exception e) {
			resultado.setError(true);
			resultado.setDescripcionError(ExceptionUtils.getMessage(e));

		}

		return resultado;
	}

	/**
	 * GetClientOpenTrad
	 *
	 * @param url
	 * @param timeout
	 * @return
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	private TranslatorV2 getClientePlata(final String url, final Long timeout) throws NumberFormatException, Exception {
		final QName SERVICE = new QName("http://inteco.minhap.gov/", "Translator_v2Service");
		final URL wsdlURL = new URL("http://pre-apertium.redsara.es:80/TranslatorService_v2/Translator_v2?wsdl");
		final TranslatorV2Service servicio = new TranslatorV2Service(wsdlURL, SERVICE);
		// final TranslatorV2Service servicio = new TranslatorV2Service();
		final TranslatorV2 serviceTasaSoap = servicio.getTranslatorV2Port();
		final BindingProvider provider = (BindingProvider) serviceTasaSoap;
		provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url);

		final Client client = ClientProxy.getClient(serviceTasaSoap);
		final HTTPConduit conduit = (HTTPConduit) client.getConduit();
		final HTTPClientPolicy httpClientPolicy = conduit.getClient();

		// Timeout
		if (timeout != null) {
			httpClientPolicy.setReceiveTimeout(timeout * 1000);
		}

		//Para poder traducir pdfs
		httpClientPolicy.setAllowChunking(false);

		// Vemos si hay que pasar por proxy
		final String proxyHost = System.getProperty("http.proxyHost");
		if (proxyHost != null && !"".equals(proxyHost)) {
			if (!validateNonProxyHosts(url)) {
				final HTTPClientPolicy policy = conduit.getClient();
				policy.setProxyServer(proxyHost);
				policy.setProxyServerPort(Integer.parseInt(System.getProperty("http.proxyPort")));

				conduit.getProxyAuthorization().setUserName(System.getProperty("http.proxyUser"));
				conduit.getProxyAuthorization().setPassword(System.getProperty("http.proxyPassword"));
			}
		}

		return serviceTasaSoap;

	}

	private String getTipoDocumento(final TipoDocumento tipoDocumento) {
		String tipo;
		if (tipoDocumento == TipoDocumento.XML) {
			tipo = "WXML";
		} else {
			tipo = tipoDocumento.toString();
		}

		return tipo;
	}

	/**
	 * Busca els host de la url indicada dentro de la propiedad http.nonProxyHosts
	 * de la JVM
	 *
	 * @param url Endpoint del ws
	 * @return true si el host esta dentro de la propiedad, fals en caso contrario
	 */
	private static boolean validateNonProxyHosts(final String url) throws Exception {
		final String nonProxyHosts = System.getProperty("http.nonProxyHosts");
		boolean existe = false;
		URL urlURL;
		try {
			if (nonProxyHosts != null && !"".equals(nonProxyHosts)) {
				urlURL = new URL(url);
				final String[] nonProxyHostsArray = nonProxyHosts.split("\\|");
				for (int i = 0; i < nonProxyHostsArray.length; i++) {
					final String a = nonProxyHostsArray[i].replaceAll("\\.", "\\\\.").replaceAll("\\*", ".*");
					;
					if (urlURL.getHost().matches(a)) {
						existe = true;
						break;
					}
				}
			}
		} catch (final MalformedURLException e) {
			LOG.error("Error al validar los nonProxyHost " + e.getCause(), e);
			throw e;
		}
		return existe;
	}

	/**
	 * Obtiene propiedad.
	 *
	 * @param propiedad propiedad
	 * @return valor
	 * @throws AutenticacionPluginException
	 */
	private String getPropiedad(final String propiedad) throws TraduccionException {
		final String res = getProperty(IMPLEMENTATION_BASE_PROPERTY + propiedad);
		if (res == null) {
			throw new TraduccionException("No se ha especificado parametro " + propiedad + " en propiedades");
		}
		return res;
	}

	/**
	 * Calcula el idioma
	 *
	 * @param idiomaEntrada
	 * @param idiomaSalida
	 * @return
	 */
	private String getIdioma(final Idioma idiomaEntrada, final Idioma idiomaSalida) {
		String idioma = idiomaEntrada.getIdioma() + "-" + idiomaSalida.getIdioma();
		// De momento, PLaTa no soporta el balear, seguramente se añadiría como _balear
		// Por tanto, el balear se enviará como catalán.
		if (idiomaEntrada == Idioma.CATALAN_BALEAR || idiomaSalida == Idioma.CATALAN_BALEAR) {
			idioma = "bal-" + idioma;
		}
		return idioma;
	}

	/**
	 * Obtiene la opcion de markunknow si se ha pasado por opciones
	 *
	 * @param opciones
	 * @return
	 */
	private String getMarkunkonw(final Opciones opciones) {
		String markUnknown;
		if (opciones != null && opciones.contains(Opciones.OPENTRAD_MARKUNKNOWN)) {
			markUnknown = opciones.getValor(Opciones.OPENTRAD_MARKUNKNOWN);
		} else {
			markUnknown = Opciones.OPENTRAD_MARKUNKNOWN_VALOR_ACTIVO;
		}
		return markUnknown;
	}

	/**
	 * Obtiene el valor de ner si se ha pasado por opciones
	 *
	 * @param opciones
	 * @return
	 */
	private Boolean getNer(final Opciones opciones) {
		Boolean ner;
		if (opciones != null && opciones.contains(Opciones.OPENTRAD_NER)) {
			ner = Boolean.valueOf(opciones.getValor(Opciones.OPENTRAD_NER));
		} else {
			ner = Boolean.valueOf(Opciones.OPENTRAD_NER_VALOR_INACTIVO);
		}
		return ner;
	}
}
