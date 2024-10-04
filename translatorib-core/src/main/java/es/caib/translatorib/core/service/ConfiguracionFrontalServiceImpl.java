package es.caib.translatorib.core.service;

import es.caib.translatorib.core.api.model.ConfiguracionFrontal;
import es.caib.translatorib.core.api.service.ConfiguracionFrontalService;
import es.caib.translatorib.core.service.repository.dao.ConfiguracionFrontalDao;
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
public class ConfiguracionFrontalServiceImpl implements ConfiguracionFrontalService {

	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(ConfiguracionFrontalServiceImpl.class);

	/** Dao. */
	@Autowired
	private ConfiguracionFrontalDao dao;

	@Override
	public void updateConfiguracionFrontal(ConfiguracionFrontal dto) {
		dao.updateConfiguracionFrontal(dto);
	}

	@Override
	public ConfiguracionFrontal findConfFrontalByDefault() {
		return dao.findConfFrontalByDefault();
	}

	@Override
	public ConfiguracionFrontal findConfFrontalById(Long id) {
		return dao.findConfFrontalById(id);
	}
}
