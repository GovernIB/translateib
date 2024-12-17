package es.caib.translatorib.service.service;

import java.io.IOException;
import java.util.*;


//import org.primefaces.model.menu.MenuModel;

import es.caib.translatorib.service.model.types.TypeRoleAcceso;
/**
 * Interface.
 *
 * @author Indra
 *
 */
public interface SessionService {


	public void reloadSession() throws IOException ;

	/** Cambio de idioma. */
	public void cambiarIdioma(final String idioma);

	/**
	 * Recarga la página principal
	 */
	public void recargaPagina();



	/** Cambio role activo. */
	public void cambiarRoleActivo(final String role) ;

	/**
	 * Redirige a la URL por defecto para el rol activo.
	 *
	 */
	public void redirectDefaultUrl();

	/**
	 * Redirige a la URL por defecto para el rol activo.
	 *
	 */
	public String getDefaultUrl();

	/**
	 * Obtiene lenguaje opuesto al seleccionado (supone solo castellano/catalan).
	 *
	 * @return lang
	 */
	public String getChangeLang() ;
	/**
	 * Limpia mochila datos.
	 */
	public void limpiaMochilaDatos();

	public void limpiaMochilaDatos(final String pClave);

	/** Genera menu segun role activo. */
	//public MenuModel getMenuModel() ;

	// --------- GETTERS / SETTERS ------------------

	public String getLiteralTituloPantalla();

	public void setLiteralTituloPantalla(final String titulo);

	public String getUserName();

	public void setUserName(final String user);

	public String getLang();

	public void setLang(final String lang);

	public Locale getLocale();

	public void setLocale(final Locale locale);

	public Map<String, Object> getMochilaDatos();

	public void setMochilaDatos(final Map<String, Object> mapaDatos);

	public List<TypeRoleAcceso> getRolesList();

	public void setRolesList(final List<TypeRoleAcceso> rolesList);

	public TypeRoleAcceso getActiveRole();

	public void setActiveRole(final TypeRoleAcceso activeRole);

	public SecurityService getSecurityService();

	public void setSecurityService(final SecurityService securityService);

	public String getLogo();

	public void setLogo(String logo);

	/**
	 * @return the hayLogo
	 */
	public boolean isHayLogo();

	/**
	 * @param hayLogo the hayLogo to set
	 */
	public void setHayLogo(boolean hayLogo);

	/**
	 * Indica si tiene el rol para ver las opciones.
	 * @return True si tiene el rol.
	 */
    public boolean tieneRol() ;

	/**
	 * Comprueba si el usuario es anónimo y en ese caso, actualiza los datos porque tiene ya datos.
	 * @return
	 */
	public boolean isAnonimo();

	/**
	 * Actualiza los datos de la sesión.
	 */
	public void actualizar() ;
	public ArrayList<String> getIdiomasBackoffice();

	public void setIdiomasBackoffice(ArrayList<String> idiomasBackoffice);

	public Boolean idiomaPermitido(String idioma);

	/**
	 * Comprueba si hay multiples idiomas permitidos.
	 * @return true si hay dos o mas idiomas permitidos.
	 */
	public Boolean hayMultiplesIdiomasPermitidos() ;


    public ArrayList<String> getIdiomasSoportadosAplicacion();

    public void setIdiomasSoportadosAplicacion(ArrayList<String> idiomasSoportadosAplicacion);
}


