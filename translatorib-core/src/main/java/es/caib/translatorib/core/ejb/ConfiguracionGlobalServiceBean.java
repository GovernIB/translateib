package es.caib.translatorib.core.ejb;

import es.caib.translatorib.core.api.model.ConfiguracionGlobal;
import es.caib.translatorib.core.api.model.Plugin;
import es.caib.translatorib.core.api.model.comun.ConstantesRolesAcceso;
import es.caib.translatorib.core.api.model.filtro.ConfiguracionGlobalFiltro;
import es.caib.translatorib.core.api.service.ConfiguracionGlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import java.util.List;

@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class ConfiguracionGlobalServiceBean implements ConfiguracionGlobalService {

	@Autowired
	private ConfiguracionGlobalService service;

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN })
	public void updateConfGlobal(ConfiguracionGlobal dto) {
		service.updateConfGlobal(dto);
	}

	@Override
 	@PermitAll
	public ConfiguracionGlobal findConfGlobalById(Long id) {
		return service.findConfGlobalById(id);
	}

	@Override
	@PermitAll
	public ConfiguracionGlobal findConfGlobalByPropiedad(String propiedad) {
		return service.findConfGlobalByPropiedad(propiedad);
	}

	@Override
	@PermitAll
	public List<ConfiguracionGlobal> listConfGlobalByFiltro(ConfiguracionGlobalFiltro filtro) {
		return service.listConfGlobalByFiltro(filtro);
	}

	@Override
	@PermitAll
	public int countConfGlobalByFiltro(ConfiguracionGlobalFiltro filtro) {
		return service.countConfGlobalByFiltro(filtro);
	}
}
