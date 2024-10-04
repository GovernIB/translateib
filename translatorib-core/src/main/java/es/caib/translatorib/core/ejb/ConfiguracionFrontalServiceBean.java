package es.caib.translatorib.core.ejb;

import es.caib.translatorib.core.api.model.ConfiguracionFrontal;
import es.caib.translatorib.core.api.model.comun.ConstantesRolesAcceso;
import es.caib.translatorib.core.api.service.ConfiguracionFrontalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class ConfiguracionFrontalServiceBean implements ConfiguracionFrontalService {

	@Autowired
	private ConfiguracionFrontalService service;

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN })
	public void updateConfiguracionFrontal(ConfiguracionFrontal dto) {
		service.updateConfiguracionFrontal(dto);
	}

	@Override
 	@PermitAll
	public ConfiguracionFrontal findConfFrontalById(Long id) {
		return service.findConfFrontalById(id);
	}

	@Override
	@PermitAll
	public ConfiguracionFrontal findConfFrontalByDefault() {
		return service.findConfFrontalByDefault();
	}

}
