package es.caib.translatorib.ejb.facade;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import es.caib.translatorib.ejb.interceptor.NegocioInterceptor;
import es.caib.translatorib.service.model.types.TypeRoleAcceso;
import es.caib.translatorib.service.service.ContextService;

/**
 * EJB que permite a los SpringBeans acceder al contexto para verificar usuario.
 *
 * @author Indra
 *
 */
@Stateless
public class ContextServiceBean implements ContextService {

	/** Inyeccion contexto EJB */
	@Resource
	private SessionContext ctx;

	@Override
	@PermitAll
	@NegocioInterceptor
	public String getUsername() {
		return ctx.getCallerPrincipal().getName();
	}

	@Override
	@PermitAll
	@NegocioInterceptor
	public boolean hashRole(final String role) {
		return ctx.isCallerInRole(role);
	}

	@Override
	@PermitAll
	@NegocioInterceptor
	public List<TypeRoleAcceso> getRoles() {
		final List<TypeRoleAcceso> lista = new ArrayList<TypeRoleAcceso>();
 		for (final TypeRoleAcceso role : TypeRoleAcceso.values()) {
			if (ctx.isCallerInRole(role.toString())) {
				lista.add(role);
			}
		}

		return lista;
	}

}
