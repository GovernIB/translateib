package es.caib.translatorib.api.test.v1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import es.caib.translatorib.api.v1.model.Idioma;
import es.caib.translatorib.api.v1.model.Opciones;
import es.caib.translatorib.api.v1.model.ParametrosTraduccionDocumento;
import es.caib.translatorib.api.v1.model.ParametrosTraduccionTexto;
import es.caib.translatorib.api.v1.model.ResultadoTraduccionDocumento;
import es.caib.translatorib.api.v1.model.ResultadoTraduccionTexto;
import es.caib.translatorib.api.v1.model.TipoDocumento;
import es.caib.translatorib.api.v1.model.TipoEntrada;
import es.caib.translatorib.api.test.v1.BasicAuthenticator;

/**
 * Clase d'exemple de client de l'api REST. Empra l'api estàndard de Client de
 * JAX-RS 2.1. El test requereix que hi hagi una unitat orgànica amb id = 1 i
 * codiDir3 = A00000001. També requereix que no existeix el codiDir3 = U87654321
 */
public class TraduccionServiceTest {

	// URL a partir de la qual estan penjats els resources.
	 private static final String BASE_URL =
	 "http://caibter.indra.es/translatorib/api/services/traduccion/v1";
	//private static final String BASE_URL = "http://localhost:8080/translatorib/api/services/traduccion/v1";

	// Nom d'usuari i password a emprar per les peticions que necesisten
	// autenticació. Cal posar un
	// usuari/password que tengui rol TIB_API a per el mòdul web de l'api REST.
	 private static final String USER = "api-tib";
	 private static final String PASSWORD = "XXXX";
	//private static final String USER = "usuario1";
	//private static final String PASSWORD = "XXXX";

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
	 */
	@Test
	public void testTraduccionText2() {

		final ParametrosTraduccionTexto parametros = new ParametrosTraduccionTexto();
		parametros.setTextoEntrada("article");
		parametros.setTipoEntrada(TipoEntrada.TEXTO_PLANO);

		parametros.setIdiomaEntrada(Idioma.CATALAN);
		parametros.setIdiomaSalida(Idioma.CASTELLANO);
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
		final String documentoEntradaEncodedString = Base64.getEncoder().encodeToString(targetArray);

		parametros.setContenidoDocumento(documentoEntradaEncodedString);
		parametros.setTipoDocumento(TipoDocumento.TXT);

		parametros.setIdiomaEntrada(Idioma.CASTELLANO);
		parametros.setIdiomaSalida(Idioma.CATALAN);

		final Response response = client.target(BASE_URL + "/documento").request().post(Entity.json(parametros));

		final ResultadoTraduccionDocumento respuesta = response.readEntity(ResultadoTraduccionDocumento.class);

		Assert.assertTrue(!respuesta.isError());
		Assert.assertTrue(respuesta != null && !respuesta.isError());

		File file = new File("P://txtTraducido.txt");

		FileOutputStream fos = null;
	    try  { 
	      fos = new FileOutputStream(file); 
	      byte[] datos = Base64.getDecoder().decode(respuesta.getTextoTraducido());
	      fos.write(datos);
	      System.out.println("TXT File Saved");
	    } catch (Exception e) {
	      e.printStackTrace();
	    } finally {
	    	fos.close();
	    }
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
		final String documentoEntradaEncodedString = Base64.getEncoder().encodeToString(targetArray);

		parametros.setContenidoDocumento(documentoEntradaEncodedString);
		parametros.setTipoDocumento(TipoDocumento.ODT);

		parametros.setIdiomaEntrada(Idioma.CASTELLANO);
		parametros.setIdiomaSalida(Idioma.CATALAN);

		final Response response = client.target(BASE_URL + "/documento").request().post(Entity.json(parametros));

		final ResultadoTraduccionDocumento respuesta = response.readEntity(ResultadoTraduccionDocumento.class);

		Assert.assertTrue(respuesta != null && !respuesta.isError());

		File file = new File("P://odtTraducido.odt");

		 FileOutputStream fos = null;
	    try {   
	    	fos = new FileOutputStream(file);
	    	byte[] datos = Base64.getDecoder().decode(respuesta.getTextoTraducido());
		    fos.write(datos);
	      System.out.println("ODT File Saved");
	    } catch (Exception e) {
	      e.printStackTrace();
	    } finally {
	    	fos.close();
	    }
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
		final String documentoEntradaEncodedString = Base64.getEncoder().encodeToString(targetArray);

		parametros.setContenidoDocumento(documentoEntradaEncodedString);
		parametros.setTipoDocumento(TipoDocumento.PDF);

		parametros.setIdiomaEntrada(Idioma.CASTELLANO);
		parametros.setIdiomaSalida(Idioma.CATALAN);

		final Response response = client.target(BASE_URL + "/documento").request().post(Entity.json(parametros));

		final ResultadoTraduccionDocumento respuesta = response.readEntity(ResultadoTraduccionDocumento.class);

		
		
		Assert.assertTrue(!respuesta.isError());
		Assert.assertTrue(respuesta != null && !respuesta.isError());

		File file = new File("P://pdfTraducido.pdf");

		FileOutputStream fos = null;
	    try {  
	      fos = new FileOutputStream(file);
	      byte[] datos = Base64.getDecoder().decode(respuesta.getTextoTraducido());
		    fos.write(datos);
	      System.out.println("PDF File Saved");
	    } catch (Exception e) {
	      e.printStackTrace();
	    } finally {
	    	fos.close();
	    }
		
		//byte[] datosTraducidos = Base64.getEncoder().encode(respuesta.getTextoTraducido().getBytes());
		//byte[] datosTraducidos = Base64.getDecoder().decode(respuesta.getTextoTraducido());
		//Path path = Paths.get("P://pdfTraducido.pdf");
		//Files.write(path, datosTraducidos);
	}
	
	
	/**
	 * Consulta totes les unitats.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testTraduccionDocDOC() throws IOException {

		final ParametrosTraduccionDocumento parametros = new ParametrosTraduccionDocumento();
		final InputStream inputStream = this.getClass().getResourceAsStream("prueba.doc");
		final byte[] targetArray = new byte[inputStream.available()];
		inputStream.read(targetArray);
		final String documentoEntradaEncodedString = Base64.getEncoder().encodeToString(targetArray);

		parametros.setContenidoDocumento(documentoEntradaEncodedString);
		parametros.setTipoDocumento(TipoDocumento.DOC);

		parametros.setIdiomaEntrada(Idioma.CASTELLANO);
		parametros.setIdiomaSalida(Idioma.CATALAN);

		final Response response = client.target(BASE_URL + "/documento").request().post(Entity.json(parametros));

		final ResultadoTraduccionDocumento respuesta = response.readEntity(ResultadoTraduccionDocumento.class);

		
		
		Assert.assertTrue(!respuesta.isError());
		Assert.assertTrue(respuesta != null && !respuesta.isError());

		File file = new File("P://docTraducido.doc");

		FileOutputStream fos = null;
	    try {  
	      fos = new FileOutputStream(file);
	      byte[] datos = Base64.getDecoder().decode(respuesta.getTextoTraducido());
		    fos.write(datos);
		    System.out.println("DOC File Saved");
	    } catch (Exception e) {
	      e.printStackTrace();
	    } finally {
	    	fos.close();
	    }
		
		//byte[] datosTraducidos = Base64.getEncoder().encode(respuesta.getTextoTraducido().getBytes());
		//byte[] datosTraducidos = Base64.getDecoder().decode(respuesta.getTextoTraducido());
		//Path path = Paths.get("P://pdfTraducido.pdf");
		//Files.write(path, datosTraducidos);
	}
	
	
	/**
	 * Consulta totes les unitats.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testTraduccionDocDOCX() throws IOException {

		final ParametrosTraduccionDocumento parametros = new ParametrosTraduccionDocumento();
		final InputStream inputStream = this.getClass().getResourceAsStream("prueba.docx");
		final byte[] targetArray = new byte[inputStream.available()];
		inputStream.read(targetArray);
		final String documentoEntradaEncodedString = Base64.getEncoder().encodeToString(targetArray);

		parametros.setContenidoDocumento(documentoEntradaEncodedString);
		parametros.setTipoDocumento(TipoDocumento.DOCX);

		parametros.setIdiomaEntrada(Idioma.CASTELLANO);
		parametros.setIdiomaSalida(Idioma.CATALAN);

		final Response response = client.target(BASE_URL + "/documento").request().post(Entity.json(parametros));

		final ResultadoTraduccionDocumento respuesta = response.readEntity(ResultadoTraduccionDocumento.class);

		
		
		Assert.assertTrue(!respuesta.isError());
		Assert.assertTrue(respuesta != null && !respuesta.isError());

		File file = new File("P://docxTraducido.docx");

		FileOutputStream fos = null;
	    try {  
	      fos = new FileOutputStream(file);
	      byte[] datos = Base64.getDecoder().decode(respuesta.getTextoTraducido());
		    fos.write(datos);
		    System.out.println("DOCX File Saved");
	    } catch (Exception e) {
	      e.printStackTrace();
	    } finally {
	    	fos.close();
	    }
		
		//byte[] datosTraducidos = Base64.getEncoder().encode(respuesta.getTextoTraducido().getBytes());
		//byte[] datosTraducidos = Base64.getDecoder().decode(respuesta.getTextoTraducido());
		//Path path = Paths.get("P://pdfTraducido.pdf");
		//Files.write(path, datosTraducidos);
	}

}
