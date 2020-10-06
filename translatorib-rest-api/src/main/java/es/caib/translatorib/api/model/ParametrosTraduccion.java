package es.caib.translatorib.api.model;

import java.io.Serializable;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import es.caib.translatorib.ejb.api.model.Idioma;
import es.caib.translatorib.ejb.api.model.Opciones;
import es.caib.translatorib.ejb.api.model.TipoEntrada;

@Schema(name = "ParametrosTraduccion")
public class ParametrosTraduccion implements Serializable {

	/** Serial version UID */
	private static final long serialVersionUID = 1L;

	private String textoEntrada;
	private TipoEntrada tipoEntrada;
	private Idioma idiomaEntrada;
	private Idioma idiomaSalida;
	private Opciones opciones;

	public String getTextoEntrada() {
		return textoEntrada;
	}

	public void setTextoEntrada(final String textoEntrada) {
		this.textoEntrada = textoEntrada;
	}

	public TipoEntrada getTipoEntrada() {
		return tipoEntrada;
	}

	public void setTipoEntrada(final TipoEntrada tipoEntrada) {
		this.tipoEntrada = tipoEntrada;
	}

	public Idioma getIdiomaEntrada() {
		return idiomaEntrada;
	}

	public void setIdiomaEntrada(final Idioma idiomaEntrada) {
		this.idiomaEntrada = idiomaEntrada;
	}

	public Idioma getIdiomaSalida() {
		return idiomaSalida;
	}

	public void setIdiomaSalida(final Idioma idiomaSalida) {
		this.idiomaSalida = idiomaSalida;
	}

	public Opciones getOpciones() {
		return opciones;
	}

	public void setOpciones(final Opciones opciones) {
		this.opciones = opciones;
	}

}
