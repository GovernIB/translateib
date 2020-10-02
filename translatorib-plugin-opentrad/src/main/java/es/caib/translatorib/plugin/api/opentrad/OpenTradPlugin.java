package es.caib.translatorib.plugin.api.opentrad;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Properties;

import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.ws.BindingProvider;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;

import es.caib.translatorib.ejb.api.model.Idioma;
import es.caib.translatorib.ejb.api.model.Opciones;
import es.caib.translatorib.ejb.api.model.Resultado;
import es.caib.translatorib.ejb.api.model.TipoDocumento;
import es.caib.translatorib.ejb.api.model.TipoEntrada;
import es.caib.translatorib.opentrad.rest.cxf.TranslatorV2;
import es.caib.translatorib.opentrad.rest.cxf.TranslatorV2Service;
import es.caib.translatorib.plugin.api.ITraduccionPlugin;
import es.caib.translatorib.plugin.api.TraduccionException;

/**
 * Interface pasarela pago.
 *
 * @author Indra
 *
 */
public class OpenTradPlugin extends AbstractPluginProperties implements ITraduccionPlugin {

	/** Prefix. */
	public static final String IMPLEMENTATION_BASE_PROPERTY = "opentrad.";

	public OpenTradPlugin(final String prefijoPropiedades, final Properties properties) {
		super(prefijoPropiedades, properties);
	}

	@Override
	public Resultado realizarTraduccion(final String textoEntrada, final TipoEntrada tipoEntrada,
			final Idioma idiomaEntrada, final Idioma idiomaSalida, final Opciones opciones) throws TraduccionException {
		final String url = getPropiedad("url");// "http://traductorbal.imaxin.com/TranslatorService_v2/Translator_v2";
		final Resultado resultado = new Resultado();
		URL wsdlURL;
		/***
		 * En caso de error: PKIX path building faild SunCertPathBuilderException... Hay
		 * que importar, bajarse el certificado del https y ejecutar:
		 *
		 * La ruta del keytool y el cacert variará según servidor (cambiar el $JAVA_HOME
		 * por la ruta correcta) :
		 *
		 * $JAVA_HOME\jre\bin>keytool -import -trustcacerts -keystore
		 * $JAVA_HOME\jre\lib\security\cacerts -storepass changeit -alias provescaib
		 * -file provescaibes.crt -noprompt
		 *
		 */

		try {
			wsdlURL = new URL(url);
		} catch (final MalformedURLException e) {
			// throw new TraduccionException("URL mal formada", e.getCause());

			resultado.setError(true);
			resultado.setDescripcionError("URL mal formada: " + ExceptionUtils.getMessage(e));
			return resultado;
		}

		// TODO Añadir parámetro
		final TranslatorV2Service ss = new TranslatorV2Service(); // (wsdlURL);
		final TranslatorV2 port = ss.getTranslatorV2Port();

		final String user = getPropiedad("user"); // "$rolsac_opentrad";
		final String pass = getPropiedad("pass"); // "dgdtgoib01";
		final Long timeout = 6000l;

		try {
			// TODO Descomentar linea
			// configurarService((BindingProvider) port, getEndpoint(url), user, pass,
			// timeout, false);
		} catch (final Exception e1) {
			// throw new TraduccionException("Mal configuracion del servicio",
			// e1.getCause());

			resultado.setError(true);
			resultado.setDescripcionError("Mal configuracion del servicio" + ExceptionUtils.getMessage(e1));
			return resultado;
		}

		try {

			final java.lang.Boolean proxyCache = null;
			final java.lang.String translationEngine = "Opentrad";
			final String documentBase64 = null;
			// final java.lang.String languagePair = "es-ca";
			final java.lang.String languagePair = idiomaEntrada.toString() + "-" + idiomaSalida.toString();

			final java.lang.Boolean ner = false;
			// final java.lang.String contentType = "txt";
			final java.lang.String markUnknown = "";
			/// final java.lang.String codeTranslate = "hola, prueba de traducción";
			final java.lang.String checksum = null;
			final java.lang.String urlX = null;
			final java.lang.String dirbase = null;

			final String textoResultado = port.translateString(proxyCache, translationEngine, documentBase64,
					languagePair, ner, tipoEntrada.toString(), markUnknown, textoEntrada, checksum, urlX, dirbase, user,
					pass);
			resultado.setError(false);
			resultado.setTextoTraducido(textoResultado);

		} catch (final Exception e) {
			// throw new TraduccionException("Error conectándose a la url", e.getCause());
			resultado.setError(true);
			resultado.setDescripcionError(ExceptionUtils.getMessage(e));

		}

		return resultado;
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

	@Override
	public Resultado realizarTraduccionDocumento(final byte[] documentoEntrada, final TipoDocumento tipoDocumento,
			final Idioma idiomaEntrada, final Idioma idiomaSalida, final Opciones opciones) throws TraduccionException {
		final String url = getPropiedad("url"); // "http://traductorbal.imaxin.com/TranslatorService_v2/Translator_v2";
		final Resultado resultado = new Resultado();
		URL wsdlURL;
		/***
		 * En caso de error: PKIX path building faild SunCertPathBuilderException... Hay
		 * que importar, bajarse el certificado del https y ejecutar:
		 *
		 * La ruta del keytool y el cacert variará según servidor (cambiar el $JAVA_HOME
		 * por la ruta correcta) :
		 *
		 * $JAVA_HOME\jre\bin>keytool -import -trustcacerts -keystore
		 * $JAVA_HOME\jre\lib\security\cacerts -storepass changeit -alias provescaib
		 * -file provescaibes.crt -noprompt
		 *
		 */

		try {
			wsdlURL = new URL(url);
		} catch (final MalformedURLException e) {
			// throw new TraduccionException("URL mal formada", e.getCause());

			resultado.setError(true);
			resultado.setDescripcionError("URL mal formada: " + ExceptionUtils.getMessage(e));
			return resultado;
		}

		// TODO Añadir parámetro
		final TranslatorV2Service ss = new TranslatorV2Service(); // (wsdlURL);
		final TranslatorV2 port = ss.getTranslatorV2Port();

		final String user = getPropiedad("user"); // "$rolsac_opentrad";
		final String pass = getPropiedad("pass"); // "dgdtgoib01";
		final Long timeout = 6000l;

		try {
			// TODO Descomentar linea
			// configurarService((BindingProvider) port, getEndpoint(url), user, pass,
			// timeout, false);
		} catch (final Exception e1) {
			// throw new TraduccionException("Mal configuracion del servicio",
			// e1.getCause());

			resultado.setError(true);
			resultado.setDescripcionError("Mal configuracion del servicio" + ExceptionUtils.getMessage(e1));
			return resultado;
		}

		try {

			final byte[] documentoEntradaEncoded = Base64.getEncoder().encode(documentoEntrada);
			final java.lang.Boolean proxyCache = null;
			final java.lang.String translationEngine = "Opentrad";
			final String documentBase64 = null;
			// final java.lang.String languagePair = "es-ca";
			final java.lang.String languagePair = idiomaEntrada.toString() + "-" + idiomaSalida.toString();

			final java.lang.Boolean ner = false;
			// final java.lang.String contentType = "txt";
			final java.lang.String markUnknown = "";
			/// final java.lang.String codeTranslate = "hola, prueba de traducción";
			final java.lang.String urlX = null;
			final java.lang.String dirbase = null;

			// final Checksum checksum = new CRC32();
			// checksum.update(documentoEntrada, 0, documentoEntrada.length);
			// final long valorChecksum = checksum.getValue();

			final MessageDigest md = MessageDigest.getInstance("MD5"); // SHA, MD2, MD5, SHA-256, SHA-38
			String valorChecksum = (new HexBinaryAdapter()).marshal(md.digest(documentoEntrada));
			final String valorChecksum2 = DatatypeConverter
					.printHexBinary(MessageDigest.getInstance("MD5").digest("a".getBytes("UTF-8")));
			valorChecksum = "6A8289620BBF89BCC28AE45B6EDFE9D5";
			// String checkSUM = checksum
			final String textoResultado = port.translateFileByte(proxyCache, translationEngine, documentBase64,
					languagePair, ner, tipoDocumento.toString(), markUnknown, documentoEntradaEncoded,
					String.valueOf(valorChecksum), urlX, dirbase, user, pass);
			resultado.setError(false);
			resultado.setTextoTraducido(textoResultado);

		} catch (final Exception e) {
			// throw new TraduccionException("Error conectándose a la url", e.getCause());
			resultado.setError(true);
			resultado.setDescripcionError(ExceptionUtils.getMessage(e));

		}

		return resultado;
	}

	/**
	 * Configura service.
	 *
	 * @param bp       Binding Provider
	 * @param endpoint Endpoint ws
	 * @param user     usuario
	 * @param pass     password
	 * @throws Exception
	 */
	private void configurarService(final BindingProvider bp, final String endpoint, final String user,
			final String pass, final Long timeout, final boolean logCalls) throws Exception {
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint);
		WsClientUtil.configurePort(bp, endpoint, user, pass, "BASIC", timeout, logCalls);
	}

	/**
	 * Extrae la url
	 *
	 * @param url
	 * @return
	 */
	private String getEndpoint(final String url) {
		String endpoint;
		if (url.endsWith("?wsdl")) {
			endpoint = url.substring(0, url.indexOf("?wsdl"));
		} else {
			endpoint = url;
		}
		return endpoint;
	}
}
