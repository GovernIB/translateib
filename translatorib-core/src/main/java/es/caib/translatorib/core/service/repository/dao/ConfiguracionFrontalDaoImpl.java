package es.caib.translatorib.core.service.repository.dao;

import es.caib.translatorib.core.api.model.ConfiguracionFrontal;
import es.caib.translatorib.core.api.model.ConfiguracionGlobal;
import es.caib.translatorib.core.api.model.filtro.ConfiguracionGlobalFiltro;
import es.caib.translatorib.core.service.component.ConfiguracionComponent;
import es.caib.translatorib.core.service.repository.model.JConfiguracionFrontal;
import es.caib.translatorib.core.service.repository.model.JConfiguracionGlobal;
import es.caib.translatorib.core.service.repository.model.JPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository("configuracionFrontalDao")
public class ConfiguracionFrontalDaoImpl implements ConfiguracionFrontalDao {

	/** EntityManager. */
	@PersistenceContext
	private EntityManager entityManager;


	@Override
	public void updateConfiguracionFrontal(ConfiguracionFrontal dto) {
		if (dto.getCodigo() == null) {
			JConfiguracionFrontal jConfiguracionFrontal = JConfiguracionFrontal.fromModel(dto);
			entityManager.persist(jConfiguracionFrontal);
		} else {
			JConfiguracionFrontal jConfiguracionFrontal = entityManager.find(JConfiguracionFrontal.class, dto.getCodigo());
			jConfiguracionFrontal.merge(dto);
			entityManager.merge(jConfiguracionFrontal);
		}
	}

	@Override
	public ConfiguracionFrontal findConfFrontalByDefault() {
		Query query = entityManager.createQuery("Select J FROM JConfiguracionFrontal J", JConfiguracionFrontal.class);
		return ((List<JConfiguracionFrontal>)query.getResultList()).get(0).toModel();
	}

	private JPlugin findPluginByCodigo(String codigoPlugin) {
		Query query = entityManager.createQuery("Select J FROM JPlugin J where J.codigo = :codigo", JPlugin.class);
		query.setParameter("codigo", Long.valueOf(codigoPlugin));
		List<JPlugin> jplugins = query.getResultList();
		if (jplugins.isEmpty()) {
			return null;
		}
		return jplugins.get(0);
	}

	@Override
	public ConfiguracionFrontal findConfFrontalById(Long id) {
		JConfiguracionFrontal jconfiguracionFrontal = entityManager.find(JConfiguracionFrontal.class, id);
		return  jconfiguracionFrontal.toModel();
	}

	/** Configuracion. */
	@Autowired
	private ConfiguracionComponent config;

	public ConfiguracionFrontalDaoImpl() {
		super();
	}


}
