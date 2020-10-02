package es.caib.translatorib.ejb;

import java.io.InputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import es.caib.translatorib.commons.utils.Constants;

@Singleton
@Startup
@RolesAllowed(Constants.TIB_ADMIN)
public class PropiedadesTraduccionEJB {

	public Properties properties;

	@PostConstruct
	public void init() {

		final String ruta = System.getProperty("es.caib.translatorib.properties.path");
		final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(ruta);

		properties = new Properties();
		System.out.println("InputStream is: " + inputStream);

		try {
			// Loading the properties
			properties.load(inputStream);

			// Printing the properties
			System.out.println("Read Properties." + properties);
		} catch (final Exception e) {
			System.out.println("Error:" + e.getMessage());
		}
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(final Properties properties) {
		this.properties = properties;
	}

}
