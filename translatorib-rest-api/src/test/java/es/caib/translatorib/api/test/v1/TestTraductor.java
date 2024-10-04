package es.caib.translatorib.api.test.v1;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
//(1) PAQUETE
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import javax.sound.sampled.Control;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Line.Info;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ListDataEvent;
import javax.swing.filechooser.FileFilter;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.FilenameUtils;
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

/**
 * Clase d'exemple de client de l'api REST. Empra l'api estàndard de Client de
 * JAX-RS 2.1.
 */
public class TestTraductor extends JFrame {

	// URL a partir de la qual estan penjats els resources.
	// private static final String BASE_URL =
	// "http://caibter.indra.es/translatorib/api/services/traduccion/v1";
	// private static final String BASE_URL =
	// "http://localhost:8080/translatorib/api/services/traduccion/v1";
	// private static final String BASE_URL =
	// "http://caibter.indra.es/translatorib/api/services/traduccion/v1";

	private static final String BASE_URL = "https://dev.caib.es/translatorib/api/services/traduccion/v1";

	// Nom d'usuari i password a emprar per les peticions que necesisten
	// autenticació. Cal posar un
	// usuari/password que tengui rol TIB_API a per el mòdul web de l'api REST.
	// private static final String USER = "api-tib";
	// private static final String PASSWORD = "M0n1n@s";

	//SI SE USA EL PLUGIN DE PLATA USAR TRADUCCION CATATAN CATALUÑA, LA BALEAR NO PARECE IR BIEN PARA LOS DOCUMENTOS
	//PLUGIN OPENTRAD YA NO ESTÁ OPERATIVO. USAR EL DE POR DEFECTO (NO ENVIAR PARAMETRO PLUGIN)
	private static final String USER = "$rolsac_translatorib";
	private static final String PASSWORD = "rolsac_translatorib";

	// Client a reutilitzar durant test
	private static Client client;

	JPanel jpanel = (JPanel) this.getContentPane();

	JTextField jtextfield = new JTextField();

	public TestTraductor() {

		// (5) PROPIEDADES DEL CONTENEDOR

		/**/
		jpanel.setLayout(null);

		// jpanel.setBackground(Color.lightGray);

		// (6) PROPIEDADES DE LOS CONTROLES

		// jtextfield.setBounds(new Rectangle(25, 15, 250, 21));

		// (8) PROPIEDADES DEL FORMULARIO

		setTitle("Traducir ficheros castellano-catalan(balear)");

		FlowLayout fl = new FlowLayout();

		setLayout(fl);

		// set up a file picker component
		final JFilePicker filePicker = new JFilePicker("Fichero a traducir", "Buscar...");
		filePicker.setMode(JFilePicker.MODE_SAVE);

		// access JFileChooser class directly
		JFileChooser fileChooser = filePicker.getFileChooser();
		fileChooser.setCurrentDirectory(new File("D:/"));

		// add the component to the frame

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1150, 700);
		setLocationRelativeTo(null); // center on screen

		JButton traducir = new JButton();

		traducir.setText("Traducir Fichero castellano-catalan");
		setUp();

		Idioma[] idiomas = new Idioma[] { Idioma.CASTELLANO, Idioma.CASTELLANO_ESPANYA, Idioma.CATALAN,
				Idioma.CATALAN_BALEAR, Idioma.CATALAN_BALEAR };

		TipoEntrada[] tiposentrada = new TipoEntrada[] { TipoEntrada.TEXTO_PLANO, TipoEntrada.HTML, TipoEntrada.XML };

		final JComboBox<Idioma> idiomaEntrada;
		idiomaEntrada = new JComboBox<Idioma>(idiomas);

		final JComboBox<Idioma> idiomaSalida;
		idiomaSalida = new JComboBox<Idioma>(idiomas);

		final JComboBox<TipoEntrada> tentrada;
		tentrada = new JComboBox<TipoEntrada>(tiposentrada);
		final JTextArea txtidi = new JTextArea(1, 50);

		idiomaEntrada.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtidi.setText(nombre((Idioma) (idiomaEntrada.getSelectedItem())) + "  -  "
						+ nombre((Idioma) (idiomaSalida.getSelectedItem())));
			}
		});

		idiomaSalida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtidi.setText(nombre((Idioma) (idiomaEntrada.getSelectedItem())) + "  -  "
						+ nombre((Idioma) (idiomaSalida.getSelectedItem())));
			}
		});

		final JTextArea txtentrada = new JTextArea(10, 100);
		final JTextArea txtsalida = new JTextArea(10, 100);

		JButton traducirS = new JButton();

		traducirS.setText("Traducir string");

		traducirS.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					txtsalida.setText(traducirString(txtentrada.getText(), (Idioma) idiomaEntrada.getSelectedItem(),
							(Idioma) idiomaSalida.getSelectedItem(), (TipoEntrada) tentrada.getSelectedItem()));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		traducir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					traducir(filePicker.getSelectedFilePath(), (Idioma) idiomaEntrada.getSelectedItem(),
							(Idioma) idiomaSalida.getSelectedItem());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		add(idiomaEntrada);
		add(idiomaSalida);
		add(txtidi);
		add(filePicker);
		add(traducir);
		add(txtentrada);
		add(txtsalida);
		add(tentrada);
		add(traducirS);
		setVisible(true);

	}

	public String nombre(Idioma i) {
		String res = "";

		switch (i) {
		case CASTELLANO_ESPANYA:
			res = "CASTELLANO_ESPANYA";
			break;
		case CATALAN:
			res = "CATALAN";
			break;
		case CASTELLANO:
			res = "CASTELLANO";
			break;
		case CATALAN_BALEAR:
			res = "CATALAN_BALEAR";
			break;

		default:
			res = i.toString();
			break;
		}

		return res;
	}

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
				respuesta != null && !respuesta.isError() && respuesta.getTextoTraducido().equals("artículo"));

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
		try {
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

		// byte[] datosTraducidos =
		// Base64.getEncoder().encode(respuesta.getTextoTraducido().getBytes());
		// byte[] datosTraducidos =
		// Base64.getDecoder().decode(respuesta.getTextoTraducido());
		// Path path = Paths.get("P://pdfTraducido.pdf");
		// Files.write(path, datosTraducidos);
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
	}

	public void traducir(String origen, Idioma entrada, Idioma salida) throws IOException {

		final ParametrosTraduccionDocumento parametros = new ParametrosTraduccionDocumento();
		// final InputStream inputStream = this.getClass().getResourceAsStream(origen);
	//	parametros.setPlugin("opentrad");

		File initialFile = new File(origen);
		InputStream inputStream = new FileInputStream(initialFile);

		final byte[] targetArray = new byte[inputStream.available()];
		inputStream.read(targetArray);
		final String documentoEntradaEncodedString = Base64.getEncoder().encodeToString(targetArray);

		File ori = new File(origen);
		String nombre = FilenameUtils.getBaseName(origen);
		String extension = FilenameUtils.getExtension(origen);
		String directorio = FilenameUtils.getFullPath(origen);

		parametros.setContenidoDocumento(documentoEntradaEncodedString);

		if (extension.equals("docx") || extension.equals("DOCX")) {
			parametros.setTipoDocumento(TipoDocumento.DOCX);
		} else if (extension.equals("pdf") || extension.equals("PDF")) {
			parametros.setTipoDocumento(TipoDocumento.PDF);
		} else if (extension.equals("doc") || extension.equals("DOC")) {
			parametros.setTipoDocumento(TipoDocumento.DOC);
		} else if (extension.equals("txt") || extension.equals("TXT")) {
			parametros.setTipoDocumento(TipoDocumento.TXT);
		} else if (extension.equals("pptx") || extension.equals("PPTX")) {
			parametros.setTipoDocumento(TipoDocumento.PPTX);
		} else if (extension.equals("xlsx") || extension.equals("XLSX")) {
			parametros.setTipoDocumento(TipoDocumento.XLSX);
		} else if (extension.equals("odt") || extension.equals("ODT")) {
			parametros.setTipoDocumento(TipoDocumento.ODT);
		} else if (extension.equals("ods") || extension.equals("ODS")) {
			parametros.setTipoDocumento(TipoDocumento.ODS);
		} else if (extension.equals("html") || extension.equals("HTML")) {
			parametros.setTipoDocumento(TipoDocumento.HTML);
		} else if (extension.equals("wxml") || extension.equals("WXML")) {
			parametros.setTipoDocumento(TipoDocumento.XML);
		} else {
			parametros.setTipoDocumento(TipoDocumento.DOCX);
		}

		// DOC("doc"), DOCX("docx"), PPTX("pptx"), XLSX("xlsx"), PDF("pdf"), ODT("odt"),
		// ODS("ods"), ODP("odp"), TXT("txt"),
		// HTML("html"), XML("wxml");

		parametros.setIdiomaEntrada(entrada);
		parametros.setIdiomaSalida(salida);

		try {

			final Response response = client.target(BASE_URL + "/documento").request().post(Entity.json(parametros));

			final ResultadoTraduccionDocumento respuesta = response.readEntity(ResultadoTraduccionDocumento.class);

			Assert.assertTrue(!respuesta.isError());
			Assert.assertTrue(respuesta != null && !respuesta.isError());

			File file = new File(directorio + nombre + "_ca." + extension);

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

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String traducirString(String texto, Idioma entrada, Idioma salida, TipoEntrada te) {
		String res = "";
		final ParametrosTraduccionTexto parametros = new ParametrosTraduccionTexto();
		parametros.setTextoEntrada(texto);
		parametros.setTipoEntrada(te);

		parametros.setIdiomaEntrada(entrada);
		parametros.setIdiomaSalida(salida);
		parametros.setOpciones(new Opciones());

		final Response response = client.target(BASE_URL + "/texto").request().post(Entity.json(parametros));

		final ResultadoTraduccionTexto respuesta = response.readEntity(ResultadoTraduccionTexto.class);

		if (respuesta != null && !respuesta.isError()) {
			res = respuesta.getTextoTraducido();
		} else {
			res = "XX ERROR XX";

		}
		return res;

	}

	public static void main(String arg[]) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new TestTraductor().setVisible(true);
			}
		});

	}

	public class JFilePicker extends JPanel {
		private String textFieldLabel;
		private String buttonLabel;

		private JLabel label;
		private JTextField textField;
		private JButton button;

		private JFileChooser fileChooser;

		private int mode;
		public static final int MODE_OPEN = 1;
		public static final int MODE_SAVE = 2;

		public JFilePicker(String textFieldLabel, String buttonLabel) {
			this.textFieldLabel = textFieldLabel;
			this.buttonLabel = buttonLabel;

			fileChooser = new JFileChooser();

			setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

			// creates the GUI
			label = new JLabel(textFieldLabel);

			textField = new JTextField(30);
			button = new JButton(buttonLabel);

			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					buttonActionPerformed(evt);
				}
			});

			add(label);
			add(textField);
			add(button);

		}

		private void buttonActionPerformed(ActionEvent evt) {
			if (mode == MODE_OPEN) {
				if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
					textField.setText(fileChooser.getSelectedFile().getAbsolutePath());
				}
			} else if (mode == MODE_SAVE) {
				if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
					textField.setText(fileChooser.getSelectedFile().getAbsolutePath());
				}
			}
		}

		public void addFileTypeFilter(String extension, String description) {
			FileTypeFilter filter = new FileTypeFilter(extension, description);
			fileChooser.addChoosableFileFilter(filter);
		}

		public void setMode(int mode) {
			this.mode = mode;
		}

		public String getSelectedFilePath() {
			return textField.getText();
		}

		public JFileChooser getFileChooser() {
			return this.fileChooser;
		}
	}

	public class FileTypeFilter extends FileFilter {

		private String extension;
		private String description;

		public FileTypeFilter(String extension, String description) {
			this.extension = extension;
			this.description = description;
		}

		@Override
		public boolean accept(File file) {
			if (file.isDirectory()) {
				return true;
			}
			return file.getName().toLowerCase().endsWith(extension);
		}

		public String getDescription() {
			return description + String.format(" (*%s)", extension);
		}
	}

}
