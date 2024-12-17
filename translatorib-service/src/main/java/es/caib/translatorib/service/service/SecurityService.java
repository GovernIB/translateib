package es.caib.translatorib.service.service;

import es.caib.translatorib.service.model.types.TypeRoleAcceso;

import java.util.List;

/**
 * Servicio para verificar accesos de seguridad.
 *
 * @author Indra
 *
 */
public interface SecurityService {

	/**
	 * Obtiene usuario autenticado.
	 *
	 * @return usuario autenticado.
	 */
	public String getUsername();

	/**
	 * Obtiene la lista de roles.
	 *
	 * @return lista de roles
	 */
	public List<TypeRoleAcceso> getRoles();

	/**
	 * Verifica si es usuario Superadministrador.
	 *
	 * @return boolean.
	 */
	public boolean isSuperAdministrador();


}
