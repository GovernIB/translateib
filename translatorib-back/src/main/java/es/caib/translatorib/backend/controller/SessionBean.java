package es.caib.translatorib.backend.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletContext;

import es.caib.translatorib.core.api.service.SecurityService;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import es.caib.translatorib.backend.util.UtilJSF;
import es.caib.translatorib.core.api.exception.ConfiguracionException;
import es.caib.translatorib.core.api.exception.ErrorBackException;
import es.caib.translatorib.core.api.model.types.TypeRoleAcceso;

/**
 * Información de sesión.
 *
 * @author Indra
 *
 */
@ManagedBean(name = "sessionBean")
@SessionScoped
public class SessionBean {

	/**
	 * Usuario.
	 */
	private String userName;

	/**
	 * Roles del usuario.
	 */
	private List<TypeRoleAcceso> rolesList;

	/**
	 * Role activo principal (superadmin).
	 */
	private TypeRoleAcceso activeRole;

	/**
	 * Idioma actual.
	 */
	private String lang;

	/**
	 * Locale actual.
	 */
	private Locale locale;

	/**
	 * Titulo pantalla.
	 */
	private String literalTituloPantalla;

	private Map<String, Object> mochilaDatos;

	private Properties propiedadesLocales;

	private String logo;

	private boolean hayLogo;

	/**
	 * Servicio seguridad.
	 */
	@Inject
	private SecurityService securityService;


	/** Inicio sesión. */
	@PostConstruct
	public void init() throws IOException {

			// Recupera info usuario
			userName = getSecurityService().getUsername();
			lang = FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage();
			locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
			rolesList = securityService.getRoles();
			final String pathProperties = System.getProperty("es.caib.translatorib.properties.path");
			// Carga fichero de propiedades
			try (FileInputStream fis = new FileInputStream(pathProperties);) {
				propiedadesLocales = new Properties();
				propiedadesLocales.load(fis);
			} catch (final IOException e) {
				throw new ConfiguracionException(e);
			}

			logo = propiedadesLocales.getProperty("back.logo");
			if (logo == null) {
				this.setHayLogo(false);
			} else {
				this.setHayLogo(true);
			}

			// Establece role activo por defecto
			if (activeRole == null) {
				if (rolesList.contains(TypeRoleAcceso.SUPER_ADMIN)) {
					activeRole = TypeRoleAcceso.SUPER_ADMIN;
				} else {
					//activeRole = null;
					//UtilJSF.redirectJsfPage("/error/errorUsuarioSinRol.xhtml", new HashMap<String, List<String>>());
					return;
				}
			}
			// inicializamos mochila
			mochilaDatos = new HashMap<String, Object>();
	}

	/** Cambio de idioma. */
	public void cambiarIdioma(final String idioma) {
		// Cambia idioma
		FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale(idioma));
		lang = FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage();
		locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();

		// Recarga pagina principal
		UtilJSF.redirectJsfDefaultPageRole(activeRole);
	}

	/** Cambio role activo. */
	public void cambiarRoleActivo(final String role) {

		// Cambia role
		final TypeRoleAcceso roleChange = TypeRoleAcceso.fromString(role);
		if (!rolesList.contains(roleChange)) {
			throw new ErrorBackException("No tiene el role indicado");
		}
		this.setActiveRole(roleChange);

		// Recarga pagina principal segun role
		UtilJSF.redirectJsfDefaultPageRole(activeRole);
	}

	/**
	 * Redirige a la URL por defecto para el rol activo.
	 *
	 */
	public void redirectDefaultUrl() {
		UtilJSF.redirectJsfDefaultPageRole(activeRole);
	}

	/**
	 * Redirige a la URL por defecto para el rol activo.
	 *
	 */
	public String getDefaultUrl() {
		final ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		final String contextPath = servletContext.getContextPath();
		return contextPath + UtilJSF.getDefaultUrlRole(activeRole);
	}

	/**
	 * Obtiene lenguaje opuesto al seleccionado (supone solo castellano/catalan).
	 *
	 * @return lang
	 */
	public String getChangeLang() {
		String res = null;
		if ("es".equals(lang)) {
			res = "ca";
		} else {
			res = "es";
		}
		return res;
	}

	/**
	 * Limpia mochila datos.
	 */
	public void limpiaMochilaDatos() {
		mochilaDatos.clear();
	}

	public void limpiaMochilaDatos(final String pClave) {
		mochilaDatos.remove(pClave);
	}

	/** Genera menu segun role activo. */
	public MenuModel getMenuModel() {
		final MenuModel model = new DefaultMenuModel();

		final DefaultSubMenu firstSubmenu = new DefaultSubMenu();
		firstSubmenu.setLabel(getUserName());
		firstSubmenu.setIcon("fa-li fa fa-user-o");
		final DefaultMenuItem item = new DefaultMenuItem();
		item.setAriaLabel(UtilJSF.getLiteral(getChangeLang()));
		item.setCommand("#{sessionBean.cambiarIdioma(sessionBean.getChangeLang())}");
		item.setIcon("fa-li fa fa-flag");
		firstSubmenu.getElements().add(item);

		model.getElements().add(firstSubmenu);

		final DefaultSubMenu secondSubmenu = new DefaultSubMenu();
		secondSubmenu.setLabel(
				UtilJSF.getLiteral("roles." + activeRole.name().toLowerCase()));
		secondSubmenu.setIcon("fa-li fa fa-id-card-o");
		for (final TypeRoleAcceso role : rolesList) {
			if (!activeRole.equals(role)) {
				final DefaultMenuItem item2 = new DefaultMenuItem();
				item2.setAriaLabel( UtilJSF.getLiteral("roles." + role.name().toLowerCase()));
				item2.setCommand("#{sessionBean.cambiarRoleActivo(\"" + role.toString() + "\")}");
				item2.setIcon("fa-li fa fa-id-card-o");
				secondSubmenu.getElements().add(item2);
			}
		}
		model.getElements().add(secondSubmenu);

		model.generateUniqueIds();
		return model;
	}

	// --------- GETTERS / SETTERS ------------------

	public String getLiteralTituloPantalla() {
		return literalTituloPantalla;
	}

	public void setLiteralTituloPantalla(final String titulo) {
		this.literalTituloPantalla = titulo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(final String user) {
		this.userName = user;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(final String lang) {
		this.lang = lang;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(final Locale locale) {
		this.locale = locale;
	}

	public Map<String, Object> getMochilaDatos() {
		return mochilaDatos;
	}

	public void setMochilaDatos(final Map<String, Object> mapaDatos) {
		this.mochilaDatos = mapaDatos;
	}

	public List<TypeRoleAcceso> getRolesList() {
		return rolesList;
	}

	public void setRolesList(final List<TypeRoleAcceso> rolesList) {
		this.rolesList = rolesList;
	}

	public TypeRoleAcceso getActiveRole() {
		return activeRole;
	}

	public void setActiveRole(final TypeRoleAcceso activeRole) {
		this.activeRole = activeRole;
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(final SecurityService securityService) {
		this.securityService = securityService;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	/**
	 * @return the hayLogo
	 */
	public boolean isHayLogo() {
		return hayLogo;
	}

	/**
	 * @param hayLogo the hayLogo to set
	 */
	public void setHayLogo(boolean hayLogo) {
		this.hayLogo = hayLogo;
	}

	/**
	 * Indica si tiene el rol para ver las opciones.
	 * @return True si tiene el rol.
	 */
    public boolean tieneRol() {
		return rolesList != null && !rolesList.isEmpty() && rolesList.contains(TypeRoleAcceso.SUPER_ADMIN);
    }

	/**
	 * Comprueba si el usuario es anónimo y en ese caso, actualiza los datos porque tiene ya datos.
	 * @return
	 */
	public boolean isAnonimo() {
		return userName == null || "anonymous".equals(userName);
	}

	/**
	 * Actualiza los datos de la sesión.
	 */
	public void actualizar() {
		userName = getSecurityService().getUsername();
		rolesList = securityService.getRoles();
		// Establece role activo por defecto
		if (activeRole == null && rolesList.contains(TypeRoleAcceso.SUPER_ADMIN)) {
			activeRole = TypeRoleAcceso.SUPER_ADMIN;
		}
	}
}
