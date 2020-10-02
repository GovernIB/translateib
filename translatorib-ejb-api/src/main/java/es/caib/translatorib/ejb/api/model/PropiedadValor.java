package es.caib.translatorib.ejb.api.model;

import java.io.Serializable;

public class PropiedadValor implements Serializable {

	/** Serial Version UID */
	private static final long serialVersionUID = 1L;

	/** Campos **/
	private String propiedad;
	private String valor;

	public String getPropiedad() {
		return propiedad;
	}

	public void setPropiedad(final String propiedad) {
		this.propiedad = propiedad;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(final String valor) {
		this.valor = valor;
	}

}
