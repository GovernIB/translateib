package es.caib.translatorib.api.test.v1;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import es.caib.translatorib.api.v1.model.ParametrosTraduccionDocumento;
import es.caib.translatorib.api.v1.model.ParametrosTraduccionTexto;
import es.caib.translatorib.ejb.api.model.Idioma;
import es.caib.translatorib.ejb.api.model.Opciones;
import es.caib.translatorib.ejb.api.model.ResultadoTraduccionDocumento;
import es.caib.translatorib.ejb.api.model.ResultadoTraduccionTexto;
import es.caib.translatorib.ejb.api.model.TipoDocumento;
import es.caib.translatorib.ejb.api.model.TipoEntrada;

/**
 * Clase d'exemple de client de l'api REST. Empra l'api estàndard de Client de
 * JAX-RS 2.1. El test requereix que hi hagi una unitat orgànica amb id = 1 i
 * codiDir3 = A00000001. També requereix que no existeix el codiDir3 = U87654321
 */
public class TraduccionServiceTest {

	// URL a partir de la qual estan penjats els resources.
	private static final String BASE_URL = "http://XXX/translatorib/api/services/traduccion/v1";
	// private static final String BASE_URL =
	// "http://localhost:8080/translatorib/api/services/traduccion/v1";

	// Nom d'usuari i password a emprar per les peticions que necesisten
	// autenticació. Cal posar un
	// usuari/password que tengui rol TIB_API a per el mòdul web de l'api REST.
	private static final String USER = "api-tib";
	private static final String PASSWORD = "XXX";
	// private static final String USER = "usuario1";
	// private static final String PASSWORD = "XXX";

	// Client a reutilitzar durant test
	private static Client client;

	@BeforeClass
	public static void setUp() {
		// Construïm un client amb autenticació
		client = ClientBuilder.newClient().register(new BasicAuthenticator(USER, PASSWORD));
	}

	/**
	 * Consulta totes les unitats.
	 */
	@Test
	public void test() {
		final ResultadoTraduccionTexto respuesta = client.target(BASE_URL + "/test").request(MediaType.APPLICATION_JSON)
				.get(ResultadoTraduccionTexto.class);

		Assert.assertTrue(!respuesta.isError());
		Assert.assertTrue("traduir".equals(respuesta.getTextoTraducido()));

	}

	/**
	 * Consulta totes les unitats.
	 */
	@Test
	public void testTraduccion() {

		final ParametrosTraduccionTexto parametros = new ParametrosTraduccionTexto();
		parametros.setTextoEntrada("Texto a traducir");
		parametros.setTipoEntrada(TipoEntrada.TEXTO_PLANO);
		parametros.setIdiomaEntrada(Idioma.CASTELLANO);
		parametros.setIdiomaSalida(Idioma.CATALAN);

		final Response response = client.target(BASE_URL + "/texto").request().post(Entity.json(parametros));

		final ResultadoTraduccionTexto respuesta = response.readEntity(ResultadoTraduccionTexto.class);

		Assert.assertTrue(!respuesta.isError());
		Assert.assertTrue(respuesta != null && !respuesta.isError());

	}

	/**
	 * Consulta totes les unitats.
	 */
	@Test
	public void testTraduccionMockup() {

		final ParametrosTraduccionTexto parametros = new ParametrosTraduccionTexto();
		parametros.setTextoEntrada("Texto a traducir");
		parametros.setTipoEntrada(TipoEntrada.TEXTO_PLANO);

		parametros.setIdiomaEntrada(Idioma.CASTELLANO);
		parametros.setIdiomaSalida(Idioma.CATALAN);
		parametros.setPlugin(ParametrosTraduccionTexto.PLUGIN_MOCKUP);
		parametros.setOpciones(new Opciones());

		final Response response = client.target(BASE_URL + "/texto").request().post(Entity.json(parametros));

		final ResultadoTraduccionTexto respuesta = response.readEntity(ResultadoTraduccionTexto.class);

		Assert.assertTrue(!respuesta.isError());
		Assert.assertTrue(respuesta != null && !respuesta.isError() && respuesta.getTextoTraducido().equals("Hola"));

	}

	/**
	 * Consulta totes les unitats.
	 */
	@Test
	public void testTraduccionText() {

		final ParametrosTraduccionTexto parametros = new ParametrosTraduccionTexto();
		parametros.setTextoEntrada("Texto a traducir");
		parametros.setTipoEntrada(TipoEntrada.TEXTO_PLANO);

		parametros.setIdiomaEntrada(Idioma.CASTELLANO);
		parametros.setIdiomaSalida(Idioma.CATALAN);
		parametros.setOpciones(new Opciones());

		final Response response = client.target(BASE_URL + "/texto").request().post(Entity.json(parametros));

		final ResultadoTraduccionTexto respuesta = response.readEntity(ResultadoTraduccionTexto.class);

		Assert.assertTrue(!respuesta.isError());
		Assert.assertTrue(
				respuesta != null && !respuesta.isError() && respuesta.getTextoTraducido().equals("Text a traduir"));

	}

	/**
	 * Consulta totes les unitats.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testTraduccionDocTxt() throws IOException {

		final ParametrosTraduccionDocumento parametros = new ParametrosTraduccionDocumento();
		final InputStream inputStream = this.getClass().getResourceAsStream("test.txt");
		final byte[] targetArray = new byte[inputStream.available()];
		inputStream.read(targetArray);

		parametros.setContenidoDocumento(targetArray);
		parametros.setTipoDocumento(TipoDocumento.TXT);

		parametros.setIdiomaEntrada(Idioma.CASTELLANO);
		parametros.setIdiomaSalida(Idioma.CATALAN);

		final Response response = client.target(BASE_URL + "/documento").request().post(Entity.json(parametros));

		final ResultadoTraduccionDocumento respuesta = response.readEntity(ResultadoTraduccionDocumento.class);

		Assert.assertTrue(!respuesta.isError());
		Assert.assertTrue(respuesta != null && !respuesta.isError());

	}

	/**
	 * Consulta totes les unitats.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testTraduccionDocOdt() throws IOException {

		final ParametrosTraduccionDocumento parametros = new ParametrosTraduccionDocumento();
		final InputStream inputStream = this.getClass().getResourceAsStream("prueba.odt");
		final byte[] targetArray = new byte[inputStream.available()];
		inputStream.read(targetArray);

		parametros.setContenidoDocumento(targetArray);
		parametros.setTipoDocumento(TipoDocumento.ODT);

		parametros.setIdiomaEntrada(Idioma.CASTELLANO);
		parametros.setIdiomaSalida(Idioma.CATALAN);

		final Response response = client.target(BASE_URL + "/documento").request().post(Entity.json(parametros));

		final ResultadoTraduccionDocumento respuesta = response.readEntity(ResultadoTraduccionDocumento.class);

		Assert.assertTrue(respuesta != null && !respuesta.isError());

	}

	/**
	 * Consulta totes les unitats.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testTraduccionDocPDF() throws IOException {

		final ParametrosTraduccionDocumento parametros = new ParametrosTraduccionDocumento();
		final InputStream inputStream = this.getClass().getResourceAsStream("test.pdf");
		final byte[] targetArray = new byte[inputStream.available()];
		inputStream.read(targetArray);

		parametros.setContenidoDocumento(targetArray);
		parametros.setTipoDocumento(TipoDocumento.PDF);

		parametros.setIdiomaEntrada(Idioma.CASTELLANO);
		parametros.setIdiomaSalida(Idioma.CATALAN);

		final Response response = client.target(BASE_URL + "/documento").request().post(Entity.json(parametros));

		final ResultadoTraduccionDocumento respuesta = response.readEntity(ResultadoTraduccionDocumento.class);

		Assert.assertTrue(!respuesta.isError());
		Assert.assertTrue(respuesta != null && !respuesta.isError());

	}

}
