package es.caib.translatorib.ejb.api.model;

import java.io.Serializable;

/***
 * Enumerado
 *
 * @author Indra
 *
 */
public enum TipoEntrada implements Serializable {
	TEXTO_PLANO("txt"), HTML("html"), WXML("wxml");

	private String entrada;

	TipoEntrada(final String iEntrada) {
		this.entrada = iEntrada;
	}

	public TipoEntrada fromString(final String tipoEntrada) {
		TipoEntrada tipo = null;
		if (tipoEntrada != null && !tipoEntrada.isEmpty()) {
			for (final TipoEntrada tip : TipoEntrada.values()) {
				if (tip.toString().equals(tipoEntrada)) {
					tipo = tip;
					break;
				}
			}
		}
		return tipo;
	}

	@Override
	public String toString() {
		return entrada;
	}

}
