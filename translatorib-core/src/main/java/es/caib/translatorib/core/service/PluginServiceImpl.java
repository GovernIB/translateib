package es.caib.translatorib.core.service;

import es.caib.translatorib.core.api.model.*;
import es.caib.translatorib.core.api.service.PluginService;
import es.caib.translatorib.core.service.component.ConfiguracionComponent;
import es.caib.translatorib.core.service.repository.dao.PluginDao;
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
public class PluginServiceImpl implements PluginService {

	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(PluginServiceImpl.class);

	@Autowired
	ConfiguracionComponent configuracionComponent;

	/** Dao. */
	@Autowired
	private PluginDao dao;

	@Override
	public List<Plugin> lista(String filtro) {
		return dao.lista(filtro);
	}

	@Override
	public Plugin getPluginByCodigo(Long codigo) {
		return dao.getPluginByCodigo(codigo);
	}

	@Override
	public void guardarPlugin(Plugin plugin) {
		dao.guardarPlugin(plugin);
	}

	@Override
	public void updatePlugin(Plugin plugin) {
		dao.updatePlugin(plugin);
	}

	@Override
	public Plugin getPluginByDefault() {
		return dao.getPluginDefault();
	}

	@Override
	public void actualizarPlg(Long codigo, String idiomasFrontal) {
		dao.actualizarPlg(codigo, idiomasFrontal);
	}
}
