package es.caib.translatorib.api.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import es.caib.translatorib.ejb.api.model.Idioma;
import es.caib.translatorib.ejb.api.model.Opciones;
import es.caib.translatorib.ejb.api.model.TipoDocumento;

@Schema(name = "ParametrosTraduccionDocumento")
public class ParametrosTraduccionDocumento {

	private byte[] contenidoDocumento;
	private TipoDocumento tipoDocumento;
	private Idioma idiomaEntrada;
	private Idioma idiomaSalida;
	private Opciones opciones;

	public byte[] getContenidoDocumento() {
		return contenidoDocumento;
	}

	public void setContenidoDocumento(final byte[] contenidoDocumento) {
		this.contenidoDocumento = contenidoDocumento;
	}

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(final TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
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
