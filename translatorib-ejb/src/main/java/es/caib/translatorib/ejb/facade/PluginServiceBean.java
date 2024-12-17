package es.caib.translatorib.ejb.facade;
import es.caib.translatorib.ejb.interceptor.ExceptionTranslate;
import es.caib.translatorib.ejb.interceptor.Logged;
import es.caib.translatorib.ejb.interceptor.NegocioInterceptor;
import es.caib.translatorib.persistence.repository.PluginRepository;
import es.caib.translatorib.service.model.Plugin;
import es.caib.translatorib.service.model.comun.ConstantesRolesAcceso;
import es.caib.translatorib.service.service.PluginService;

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

@Logged
@ExceptionTranslate
@Stateless
@Local(PluginService.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class PluginServiceBean implements PluginService {

	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(PluginServiceBean.class);

	/** Dao. */
	@Inject
	private PluginRepository dao;

	/** METODOS LLAMADOS DESDE VIEWTRADUCIR **/
	@Override
	@PermitAll
	@NegocioInterceptor
	public Plugin getPluginByCodigo(Long codigo) {
		LOG.debug("getPluginByCodigo");
		return dao.getPluginByCodigo(codigo);
	}

	@Override
	@PermitAll
	@NegocioInterceptor
	public List<Plugin> lista(String filtro) {
		LOG.debug("lista. Filtro: {}" , filtro);
		return dao.lista(filtro);
	}

	@Override
	@PermitAll
	@NegocioInterceptor
	public Plugin getPluginByDefault() {
		LOG.debug("getPluginByDefault");
		return dao.getPluginDefault();
	}

	/** MÉTODOS A NIVEL SUPERADMIN **/
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN })
	@NegocioInterceptor
	public void guardarPlugin(Plugin plugin) {
		LOG.debug("guardarPlugin. Plugin: {}" , plugin);
		dao.guardarPlugin(plugin);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN })
	@NegocioInterceptor
	public void updatePlugin(Plugin plugin) {
		LOG.debug("updatePlugin. Plugin: {}" , plugin);
		dao.updatePlugin(plugin);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN })
	@NegocioInterceptor
	public void actualizarPlg(Long codigo, String idiomasFrontal) {
		LOG.debug("actualizarPlg. Codigo: {}" , codigo);
		dao.actualizarPlg(codigo, idiomasFrontal);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN })
	@NegocioInterceptor
	public void borrar(Plugin plugin) {
		LOG.debug("borrar. Plugin: {}" , plugin);
		dao.borrar(plugin);
	}
}
