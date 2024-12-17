package es.caib.translatorib.ejb.facade;

import es.caib.translatorib.ejb.interceptor.ExceptionTranslate;
import es.caib.translatorib.ejb.interceptor.Logged;
import es.caib.translatorib.ejb.service.component.ConfiguracionComponent;
import es.caib.translatorib.persistence.repository.ConfiguracionGlobalRepository;
import es.caib.translatorib.service.model.ConfiguracionGlobal;
import es.caib.translatorib.service.model.comun.ConstantesRolesAcceso;
import es.caib.translatorib.service.model.filtro.ConfiguracionGlobalFiltro;
import es.caib.translatorib.service.service.ConfiguracionGlobalService;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import es.caib.translatorib.ejb.interceptor.NegocioInterceptor;

@Logged
@ExceptionTranslate
@Stateless
@Local(ConfiguracionGlobalService.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ConfiguracionGlobalServiceBean implements ConfiguracionGlobalService {

	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(ConfiguracionGlobalServiceBean.class);

	/**
	 * Dao.
	 */
	@Inject
	private ConfiguracionGlobalRepository dao;

	@Inject
	private ConfiguracionComponent configuracionComponent;

	@Override
	@RolesAllowed({ConstantesRolesAcceso.SUPER_ADMIN})
	@NegocioInterceptor
	public void updateConfGlobal(ConfiguracionGlobal dto) {
		LOG.debug("updateConfGlobal. DTO:{}", dto);
		dao.updateConfiguracionGlobal(dto);
	}

	@Override
	@PermitAll
	@NegocioInterceptor
	public ConfiguracionGlobal findConfGlobalById(Long id) {
		LOG.debug("findConfGlobalById.ID:{}", id);
		return dao.getConfiguracionGlobalByCodigo(id);
	}

	/** MÉTODOS UTILIZADOS DESDE VIEWTRADUCIR **/
	@Override
	@PermitAll
	@NegocioInterceptor
	public ConfiguracionGlobal findConfGlobalByPropiedad(String propiedad) {
		LOG.debug("findConfGlobalByPropiedad. Propiedad:{}", propiedad);
		ConfiguracionGlobal conf = dao.getConfiguracionGlobalByPropiedad(propiedad);
		if (conf == null || conf.getValor() == null || conf.getValor().isEmpty()) {
			String valor = configuracionComponent.readPropiedad(propiedad);
			if (valor != null && valor.isEmpty()) {
				conf = new ConfiguracionGlobal();
				conf.setPropiedad(propiedad);
				conf.setValor(valor);
			}
		}
		return conf;
	}

	@Override
	@PermitAll
	@NegocioInterceptor
	public String valorByPropiedad(String propiedad) {
		LOG.debug("valorByPropiedad. Propiedad:{}", propiedad);
		ConfiguracionGlobal conf = dao.getConfiguracionGlobalByPropiedad(propiedad);
		if (conf == null || conf.getValor() == null || conf.getValor().isEmpty()) {
			return configuracionComponent.readPropiedad(propiedad);
		} else {
			return conf.getValor();
		}
	}

	/** MÉTODOS A NIVEL SUPERADMIN **/
	@Override
	@RolesAllowed({ConstantesRolesAcceso.SUPER_ADMIN})
	@NegocioInterceptor
	public List<ConfiguracionGlobal> listConfGlobalByFiltro(ConfiguracionGlobalFiltro filtro) {
		LOG.debug("listConfGlobalByFiltro. Filtro:{}", filtro);
		return dao.findPagedByFiltro(filtro);
	}

	@Override
	@RolesAllowed({ConstantesRolesAcceso.SUPER_ADMIN})
	@NegocioInterceptor
	public int countConfGlobalByFiltro(ConfiguracionGlobalFiltro filtro) {
		LOG.debug("countConfGlobalByFiltro. Filtro:{}", filtro);
		return dao.countByFiltro(filtro);
	}
}
