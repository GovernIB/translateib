package es.caib.translatorib.ejb;

import java.io.FileInputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.translatorib.commons.utils.Constants;

@Singleton
@Startup
@RolesAllowed(Constants.TIB_ADMIN)
public class PropiedadesTraduccionEJB {

	private static final Logger LOG = LoggerFactory.getLogger(TraduccionEJB.class);

	public Properties properties;

	@PostConstruct
	public void init() {

		String ruta = System.getProperty("es.caib.translatorib.properties.path");
		// final InputStream inputStream =
		// this.getClass().getClassLoader().getResourceAsStream(ruta);
		// this.getClass().getClassLoader().getResourceAsStream("P:/app/caib/translatorib/translatorib.properties");
		// new
		// java.io.FileInputStream("\\app\\caib\\translatorib\\translatorib.properties");
		//ruta = "\\app\\caib\\translatorib\\translatorib.properties";
		properties = new Properties();
		FileInputStream inputStream = null;

		try {
			// Loading the properties

			inputStream = new FileInputStream(ruta);

			properties.load(inputStream);

			// Printing the properties
			LOG.error("Ha leido correctamente, properties:" + properties);

		} catch (final Exception e) {
			LOG.error("Ha fallado la lectura de propiedades, ruta:", ruta);
			LOG.error("Ha fallado la lectura de propiedades", e);

		}
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(final Properties properties) {
		this.properties = properties;
	}

}
