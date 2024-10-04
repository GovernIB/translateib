package es.caib.translatorib.core.api.model;

import java.io.Serializable;

/***
 * Enumerado
 *
 * @author Indra
 *
 */
public enum TipoEntrada implements Serializable {
	TEXTO_PLANO("txt"), HTML("html"), XML("xml");

	private String entrada;

	TipoEntrada(final String iEntrada) {
		this.entrada = iEntrada;
	}

	/** From string. **/
	public static TipoEntrada fromString(final String tipoEntrada) {
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
