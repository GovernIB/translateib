package es.caib.translatorib.ejb.facade;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import es.caib.translatorib.ejb.interceptor.ExceptionTranslate;
import es.caib.translatorib.ejb.interceptor.Logged;
import es.caib.translatorib.service.service.SecurityService;

import es.caib.translatorib.service.model.types.TypeRoleAcceso;
import es.caib.translatorib.ejb.interceptor.NegocioInterceptor;
import es.caib.translatorib.service.service.ContextService;
/**
 * Servicio para verificar accesos de seguridad.
 *
 * @author Indra
 *
 */

@Logged
@ExceptionTranslate
@Stateless
@Local(SecurityService.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class SecurityServiceBean implements SecurityService {

	/** Security service. */
	@Inject
	private ContextService contextService;

	@Override
	@PermitAll
	@NegocioInterceptor
	public boolean isSuperAdministrador() {
		return contextService.getRoles().contains(TypeRoleAcceso.SUPER_ADMIN);
	}


	@Override
	@PermitAll
	@NegocioInterceptor
	public List<TypeRoleAcceso> getRoles() {
		return contextService.getRoles();
	}

	@Override
	@PermitAll
	@NegocioInterceptor
	public String getUsername() {
		return contextService.getUsername();
	}


}
