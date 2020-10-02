package es.caib.translatorib.ejb.api.model;

import java.io.Serializable;

/***
 * Enumerado
 *
 * @author Indra
 *
 */
public enum Idioma implements Serializable {
	CASTELLANO("es"), CASTELLANO_ESPAÑA("es_ES"), CASTELLANO_MEJICO("es_MX"), CATALAN("ca"), CATALAN_CATALUÑA("ca_ES"),
	CATALAN_BALEAR("ca_ES");

	private String valor;

	/** Constructor. **/
	Idioma(final String ivalor) {
		this.valor = ivalor;
	}

	@Override
	public String toString() {
		return this.valor;
	}
}
