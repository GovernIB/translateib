package es.caib.translatorib.api.test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import es.caib.translatorib.api.model.ParametrosTraduccion;
import es.caib.translatorib.api.model.ParametrosTraduccionDoc;
import es.caib.translatorib.ejb.api.model.Idioma;
import es.caib.translatorib.ejb.api.model.Opciones;
import es.caib.translatorib.ejb.api.model.Resultado;
import es.caib.translatorib.ejb.api.model.TipoDocumento;
import es.caib.translatorib.ejb.api.model.TipoEntrada;

/**
 * Clase d'exemple de client de l'api REST. Empra l'api estàndard de Client de
 * JAX-RS 2.1. El test requereix que hi hagi una unitat orgànica amb id = 1 i
 * codiDir3 = A00000001. També requereix que no existeix el codiDir3 = U87654321
 */
public class TraduccionServiceTest {

	// URL a partir de la qual estan penjats els resources.
	private static final String BASE_URL = "http://localhost:8080/translatorib/api/services/traduccion";

	// Nom d'usuari i password a emprar per les peticions que necesisten
	// autenticació. Cal posar un
	// usuari/password que tengui rol TIB_ADMIN a per el mòdul web de l'api REST.
	private static final String USER = "usuario1";
	private static final String PASSWORD = "1234";

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
		final Resultado respuesta = client.target(BASE_URL + "/test").request(MediaType.APPLICATION_JSON)
				.get(Resultado.class);

		Assert.assertTrue(!respuesta.isError());
		Assert.assertTrue("traduir".equals(respuesta.getTextoTraducido()));

	}

	/**
	 * Consulta totes les unitats.
	 */
	@Test
	public void testTraduccion() {

		final ParametrosTraduccion parametros = new ParametrosTraduccion();
		parametros.setTextoEntrada("Texto a traducir");
		parametros.setTipoEntrada(TipoEntrada.TEXTO_PLANTO);

		parametros.setIdiomaEntrada(Idioma.CASTELLANO);
		parametros.setIdiomaSalida(Idioma.CATALAN);
//		final Resultado postResponse = client.target(BASE_URL + "/realizarTraduccion").request()
//				.post(Entity.json(parametros), Resultado.class);

		final Response response = client.target(BASE_URL + "/realizarTraduccion").request()
				.post(Entity.json(parametros));

		final Resultado respuesta = response.readEntity(Resultado.class);

		Assert.assertTrue(!respuesta.isError());
		Assert.assertTrue(respuesta != null && !respuesta.isError());

	}

	/**
	 * Consulta totes les unitats.
	 */
	@Test
	public void testTraduccionMockup() {

		final ParametrosTraduccion parametros = new ParametrosTraduccion();
		parametros.setTextoEntrada("Texto a traducir");
		parametros.setTipoEntrada(TipoEntrada.TEXTO_PLANTO);

		parametros.setIdiomaEntrada(Idioma.CASTELLANO);
		parametros.setIdiomaSalida(Idioma.CATALAN);
		final Opciones opciones = new Opciones();
		opciones.addPropiedadValor(Opciones.PLUGIN, "es.caib.translatorib.plugin.api.mock.MockTradPlugin");
		parametros.setOpciones(opciones);
//		final Resultado postResponse = client.target(BASE_URL + "/realizarTraduccion").request()
//				.post(Entity.json(parametros), Resultado.class);

		final Response response = client.target(BASE_URL + "/realizarTraduccion").request()
				.post(Entity.json(parametros));

		final Resultado respuesta = response.readEntity(Resultado.class);

		Assert.assertTrue(!respuesta.isError());
		Assert.assertTrue(respuesta != null && !respuesta.isError() && respuesta.getTextoTraducido().equals("Hola"));

	}

	/**
	 * Consulta totes les unitats.
	 */
	@Test
	public void testTraduccionDoc() {

		final ParametrosTraduccionDoc parametros = new ParametrosTraduccionDoc();
		parametros.setContenidoDocumento("Texto a traducir".getBytes());
		parametros.setTipoDocumento(TipoDocumento.TEXTO_PLANTO);

		parametros.setIdiomaEntrada(Idioma.CASTELLANO);
		parametros.setIdiomaSalida(Idioma.CATALAN);
//		final Resultado postResponse = client.target(BASE_URL + "/realizarTraduccion").request()
//				.post(Entity.json(parametros), Resultado.class);

		final Response response = client.target(BASE_URL + "/realizarTraduccionDocumento").request()
				.post(Entity.json(parametros));

		final Resultado respuesta = response.readEntity(Resultado.class);

		Assert.assertTrue(!respuesta.isError());
		Assert.assertTrue(respuesta != null && !respuesta.isError());

	}

}
