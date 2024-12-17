package es.caib.translatorib.ejb.facade;

import es.caib.translatorib.ejb.interceptor.ExceptionTranslate;
import es.caib.translatorib.ejb.interceptor.Logged;
import es.caib.translatorib.persistence.repository.ConfiguracionFrontalRepository;
import es.caib.translatorib.service.model.ConfiguracionFrontal;
import es.caib.translatorib.service.model.comun.ConstantesRolesAcceso;
import es.caib.translatorib.service.service.ConfiguracionFrontalService;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import es.caib.translatorib.ejb.interceptor.NegocioInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Logged
@ExceptionTranslate
@Stateless
@Local(ConfiguracionFrontalService.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ConfiguracionFrontalServiceBean implements ConfiguracionFrontalService {


	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(ConfiguracionFrontalServiceBean.class);

	/** Dao. */
	@Inject
	private ConfiguracionFrontalRepository dao;

	/** METODO PARA VIEWTRADUCIR **/
	@Override
	@NegocioInterceptor
	@PermitAll
	public ConfiguracionFrontal findConfFrontalByDefault() {
		LOG.debug("findConfFrontalByDefault");
		return dao.findConfFrontalByDefault();
	}

	/** METODO PARA SUPERADMIN **/
	@Override
	@NegocioInterceptor
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN })
	public void updateConfiguracionFrontal(ConfiguracionFrontal dto) {
		LOG.debug("updateConfiguracionFrontal. DTO:{}",dto);
		dao.updateConfiguracionFrontal(dto);
	}


	@Override
	@NegocioInterceptor
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN })
	public ConfiguracionFrontal findConfFrontalById(Long id) {
		LOG.debug("findConfFrontalById.ID:{}" , id);
		return dao.findConfFrontalById(id);
	}

}
