package es.caib.translatorib.backend.controller;

import es.caib.translatorib.backend.model.DialogResult;
import es.caib.translatorib.backend.util.UtilJSF;
import es.caib.translatorib.service.model.comun.Propiedad;
import es.caib.translatorib.service.model.types.TypeModoAcceso;
import es.caib.translatorib.service.service.SessionService;
import es.caib.translatorib.service.util.UtilJSON;

import java.io.Serializable;

import javax.ejb.SessionBean;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

@ManagedBean
@ViewScoped
public class DialogPropiedad  extends ViewControllerBase implements Serializable {


	/**
	 * Dato elemento en formato JSON.
	 */
	private String iData;

	/**
	 * Datos elemento.
	 */
	private Propiedad data;

	/**
	 * Datos elemento.
	 */
	private Propiedad dataOriginal;

	/**
	 * Parametro de entrada para ocultar 'valor'.
	 */
	private String ocultarValor;

	/**
	 * Muestra el valor.
	 */
	private boolean mostrarValor = true;

	/**
	 * Inicialización.
	 */
	public void load() {
		if (this.isAlta()) {
			data = new Propiedad();
		} else {
			data = (Propiedad) UtilJSON.fromJSON(iData, Propiedad.class);
			if (data == null) {
				data = new Propiedad();
			}
			dataOriginal = data.clone();
		}
		if (ocultarValor != null && "S".equals(ocultarValor)) {
			mostrarValor = false;
		}
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {
		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(getModoAcceso()));
		result.setResult(data);
		UtilJSF.closeDialog(result);
	}

	/**
	 * Cancelar
	 */
	public void cancelar() {
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(getModoAcceso()));
		result.setCanceled(true);
		UtilJSF.closeDialog(result);
	}

	/**
	 * @return the iData
	 */
	public String getiData() {
		return iData;
	}

	/**
	 * @param iData
	 *            the iData to set
	 */
	public void setiData(final String iData) {
		this.iData = iData;
	}

	/**
	 * @return the data
	 */
	public Propiedad getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(final Propiedad data) {
		this.data = data;
	}

	/**
	 * @return the mostrarValor
	 */
	public boolean isMostrarValor() {
		return mostrarValor;
	}

	/**
	 * @param mostrarValor
	 *            the mostrarValor to set
	 */
	public void setMostrarValor(final boolean mostrarValor) {
		this.mostrarValor = mostrarValor;
	}

	/**
	 * @return the ocultarValor
	 */
	public String getOcultarValor() {
		return ocultarValor;
	}

	/**
	 * @param ocultarValor
	 *            the ocultarValor to set
	 */
	public void setOcultarValor(final String ocultarValor) {
		this.ocultarValor = ocultarValor;
	}

	public Propiedad getDataOriginal() {
		return dataOriginal;
	}

	public void setDataOriginal(Propiedad dataOriginal) {
		this.dataOriginal = dataOriginal;
	}

}
