package es.caib.translatorib.plugin.api.opentrad;

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
import es.caib.translatorib.opentrad.rest.cxf.TranslatorV2;
import es.caib.translatorib.opentrad.rest.cxf.TranslatorV2Service;
import es.caib.translatorib.plugin.api.ITraduccionPlugin;
import es.caib.translatorib.plugin.api.TraduccionException;

/**
 * Interface pasarela pago.
 * 
 * @see Solucion al problema de proxy:
 *      https://stackoverflow.com/questions/19815145/jax-ws-client-without-a-wsdl-document-file
 * @author Indra
 *
 */
public class OpenTradPlugin extends AbstractPluginProperties implements ITraduccionPlugin {

	private static final Logger LOG = LoggerFactory.getLogger(OpenTradPlugin.class);

	/** Prefix. */
	public static final String IMPLEMENTATION_BASE_PROPERTY = "traductor.opentrad.";

	public OpenTradPlugin(final String prefijoPropiedades, final Properties properties) {
		super(prefijoPropiedades, properties);
	}

	@Override
	public ResultadoTraduccionTexto realizarTraduccion(final String textoEntrada, final TipoEntrada tipoEntrada,
			final Idioma idiomaEntrada, final Idioma idiomaSalida, final Opciones opciones) throws TraduccionException {
		final ResultadoTraduccionTexto resultado = new ResultadoTraduccionTexto();
		final String url = getPropiedad("url");
		final String user = getPropiedad("user");
		final String pass = getPropiedad("pass");
		final Long timeout = Long.valueOf(getPropiedad("timeout"));

		try {

			final TranslatorV2 port = getClienteOpenTrad(url, timeout);

			final java.lang.Boolean proxyCache = null;
			final java.lang.String translationEngine = "Opentrad";
			final String documentBase64 = null;
			// final java.lang.String languagePair = "es-ca";
			final java.lang.String languagePair = getIdioma(idiomaEntrada, idiomaSalida);

			final java.lang.Boolean ner = false;
			// final java.lang.String contentType = "txt";
			final java.lang.String markUnknown = "";
			/// final java.lang.String codeTranslate = "hola, prueba de traducción";
			final java.lang.String checksum = null;
			final java.lang.String urlX = null;
			final java.lang.String dirbase = null;
			final java.lang.String openTipoEntrada;
			if (tipoEntrada == TipoEntrada.XML) {
				openTipoEntrada = "WXML";
			} else {
				openTipoEntrada = tipoEntrada.toString();
			}
			final String textoResultado = port.translateString(proxyCache, translationEngine, documentBase64,
					languagePair, ner, openTipoEntrada, markUnknown, textoEntrada, checksum, urlX, dirbase, user, pass);
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
	public ResultadoTraduccionDocumento realizarTraduccionDocumento(final byte[] documentoEntrada,
			final TipoDocumento tipoDocumento, final Idioma idiomaEntrada, final Idioma idiomaSalida,
			final Opciones opciones) throws TraduccionException {
		final ResultadoTraduccionDocumento resultado = new ResultadoTraduccionDocumento();
		final String url = getPropiedad("url");
		final String user = getPropiedad("user");
		final String pass = getPropiedad("pass");
		final Long timeout = Long.valueOf(getPropiedad("timeout"));

		try {

			final TranslatorV2 port = getClienteOpenTrad(url, timeout);

			final byte[] documentoEntradaEncoded = Base64.getEncoder().encode(documentoEntrada);
			final String documentoEntradaEncodedString = Base64.getEncoder().encodeToString(documentoEntrada);
			final java.lang.Boolean proxyCache = null;
			final java.lang.String translationEngine = "Opentrad";
			final java.lang.String languagePair = getIdioma(idiomaEntrada, idiomaSalida);

			final java.lang.Boolean ner = false;
			final java.lang.String markUnknown = "";
			final java.lang.String urlX = null;
			final java.lang.String dirbase = null;
			final java.lang.String tipoDocumentoStr = getTipoDocumento(tipoDocumento);

			// final Checksum checksum = new CRC32();
			// checksum.update(documentoEntrada, 0, documentoEntrada.length);
			// final long valorChecksum = checksum.getValue();

			// SHA, MD2, MD5, SHA-256, SHA-38
			final String valorChecksum = (new HexBinaryAdapter())
					.marshal(MessageDigest.getInstance("MD5").digest(documentoEntrada));

			// String checkSUM = checksum
			final String textoResultado = port.translateFileByte(proxyCache, translationEngine,
					documentoEntradaEncodedString, languagePair, ner, tipoDocumentoStr, markUnknown,
					documentoEntradaEncoded, String.valueOf(valorChecksum), urlX, dirbase, user, pass);
			resultado.setError(false);
			resultado.setTextoTraducido(textoResultado);

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
	private TranslatorV2 getClienteOpenTrad(final String url, final Long timeout)
			throws NumberFormatException, Exception {
		final QName SERVICE = new QName("http://inteco.minhap.gov/", "Translator_v2Service");
		final TranslatorV2Service servicio = new TranslatorV2Service(null, SERVICE);
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
		if (idiomaEntrada == Idioma.CATALAN_BALEAR || idiomaSalida == Idioma.CATALAN_BALEAR) {
			idioma = "bal-" + idioma;
		}
		return idioma;
	}

}
