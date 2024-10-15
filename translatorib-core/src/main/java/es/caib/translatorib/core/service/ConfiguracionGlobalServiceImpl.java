package es.caib.translatorib.core.service;

import es.caib.translatorib.core.api.model.ConfiguracionGlobal;
import es.caib.translatorib.core.api.model.filtro.ConfiguracionGlobalFiltro;
import es.caib.translatorib.core.api.service.ConfiguracionGlobalService;
import es.caib.translatorib.core.service.repository.dao.ConfiguracionGlobalDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * Servei para gestionar la traduccion.
 *
 * @author Indra
 */
@Service
@Transactional
public class ConfiguracionGlobalServiceImpl implements ConfiguracionGlobalService {

	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(ConfiguracionGlobalServiceImpl.class);

	/** Dao. */
	@Autowired
	private ConfiguracionGlobalDao dao;



	@Override
	public void updateConfGlobal(ConfiguracionGlobal dto) {
		dao.updateConfiguracionGlobal(dto);
	}

	@Override
	public ConfiguracionGlobal findConfGlobalById(Long id) {
		return dao.getConfiguracionGlobalByCodigo(id);
	}

	@Override
	public ConfiguracionGlobal findConfGlobalByPropiedad(String propiedad) {
		ConfiguracionGlobal conf =  dao.getConfiguracionGlobalByPropiedad(propiedad);
		if (conf == null || conf.getValor() == null || conf.getValor().isEmpty()) {
			String valor = getPropiedadSistema(propiedad);
			if (valor != null && valor.isEmpty()) {
				conf = new ConfiguracionGlobal();
				conf.setPropiedad(propiedad);
				conf.setValor(valor);
			}
		}
		return conf;
	}

	@Override
	public String valorByPropiedad(String propiedad) {
		ConfiguracionGlobal conf =  dao.getConfiguracionGlobalByPropiedad(propiedad);
		if (conf == null || conf.getValor() == null || conf.getValor().isEmpty()) {
			return getPropiedadSistema(propiedad);
		} else {
			return conf.getValor();
		}
	}

	private String getPropiedadSistema(String propiedad) {
		String ruta = System.getProperty("es.caib.translatorib.properties.path");
		if (ruta == null) {
			return null;
		}
		//Cargar las propiedades a partir de la ruta
		Properties propiedades = new Properties();
		try (FileInputStream fis = new FileInputStream(ruta);) {
			propiedades.load(fis);
		} catch (final IOException e) {
			LOG.error("Error cargando el fichero en la ruta " + ruta, e);
			return "";
		}
		return propiedades.getProperty(propiedad);
	}

	@Override
	public List<ConfiguracionGlobal> listConfGlobalByFiltro(ConfiguracionGlobalFiltro filtro) {
		return dao.findPagedByFiltro(filtro);
	}

	@Override
	public int countConfGlobalByFiltro(ConfiguracionGlobalFiltro filtro) {
		return dao.countByFiltro(filtro);
	}
}
