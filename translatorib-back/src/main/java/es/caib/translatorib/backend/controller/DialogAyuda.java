package es.caib.translatorib.backend.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import es.caib.translatorib.backend.model.DialogResult;
import es.caib.translatorib.core.api.model.types.TypeModoAcceso;
import es.caib.translatorib.backend.util.UtilJSF;

/**
 * Disenyo formulario.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class DialogAyuda extends DialogControllerBase {

	/** Id formulario **/
	private String id;

	/** Url iframe. **/
	private String urlIframe;

	/**
	 * Inicializacion.
	 **/
	public void init() {
		urlIframe = "AyudaServlet?ts=" + System.currentTimeMillis() + "&id=" + id + "&lang="
				+ UtilJSF.getIdioma();
	}

	/**
	 * Botón de cancelar la ayuda.
	 */
	public void cancelar() {
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(true);
		UtilJSF.closeDialog(result);
	}

	/**
	 * @return the id
	 */
	public final String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public final void setId(final String id) {
		this.id = id;
	}

	/**
	 * @return the urlIframe
	 */
	public final String getUrlIframe() {
		return urlIframe;
	}

	/**
	 * @param urlIframe
	 *            the urlIframe to set
	 */
	public final void setUrlIframe(final String urlIframe) {
		this.urlIframe = urlIframe;
	}

}
