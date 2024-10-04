package es.caib.translatorib.core.ejb;

import es.caib.translatorib.core.api.model.*;
import es.caib.translatorib.core.api.model.comun.ConstantesRolesAcceso;
import es.caib.translatorib.core.api.service.PluginService;
import es.caib.translatorib.core.api.service.TraduccionService;
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
public class PluginServiceBean implements PluginService {

	@Autowired
	private PluginService service;

	@Override
	//@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN })
	@PermitAll
	public List<Plugin> lista(String filtro) {
		return service.lista(filtro);
	}

	@Override
	//@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN })
	@PermitAll
	public Plugin getPluginByCodigo(Long codigo) {
		return service.getPluginByCodigo(codigo);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN })
	public void guardarPlugin(Plugin plugin) {
		service.guardarPlugin(plugin);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN })
	public void updatePlugin(Plugin plugin) {
		service.updatePlugin(plugin);
	}

	@Override
	@PermitAll
	public Plugin getPluginByDefault() {
		return service.getPluginByDefault();
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN })
	public void actualizarPlg(Long codigo, String idiomasFrontal) {
		service.actualizarPlg(codigo, idiomasFrontal);
	}
}
