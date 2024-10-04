package es.caib.translatorib.core.service;

import es.caib.translatorib.core.api.model.ConfiguracionGlobal;
import es.caib.translatorib.core.api.model.filtro.ConfiguracionGlobalFiltro;
import es.caib.translatorib.core.api.service.ConfiguracionGlobalService;
import es.caib.translatorib.core.service.component.ConfiguracionComponent;
import es.caib.translatorib.core.service.repository.dao.ConfiguracionGlobalDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
		return dao.getConfiguracionGlobalByPropiedad(propiedad);
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
