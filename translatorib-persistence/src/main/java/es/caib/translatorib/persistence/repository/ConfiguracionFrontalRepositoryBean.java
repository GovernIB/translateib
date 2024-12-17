package es.caib.translatorib.persistence.repository;

import es.caib.translatorib.persistence.model.JConfiguracionFrontal;
import es.caib.translatorib.persistence.model.JPlugin;
import es.caib.translatorib.service.model.ConfiguracionFrontal;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;
import java.util.List;

@Stateless
@Local(ConfiguracionFrontalRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class ConfiguracionFrontalRepositoryBean extends AbstractCrudRepository<JConfiguracionFrontal, Long> implements ConfiguracionFrontalRepository {


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


	public ConfiguracionFrontalRepositoryBean() {
		super(JConfiguracionFrontal.class);
	}


}
