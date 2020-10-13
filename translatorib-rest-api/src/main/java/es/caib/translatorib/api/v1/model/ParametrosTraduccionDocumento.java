package es.caib.translatorib.api.v1.model;

import java.io.Serializable;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import es.caib.translatorib.ejb.api.model.Idioma;
import es.caib.translatorib.ejb.api.model.Opciones;
import es.caib.translatorib.ejb.api.model.TipoDocumento;

@Schema(name = "ParametrosTraduccionDocumento")
public class ParametrosTraduccionDocumento extends ParametrosTraduccion implements Serializable {

	/** Serial Version UID */
	private static final long serialVersionUID = 1L;

	/** Contenido documento **/
	private byte[] contenidoDocumento;

	/** Tipo de documento **/
	private TipoDocumento tipoDocumento;

	/** Idioma de entrada. **/
	private Idioma idiomaEntrada;

	/** Idioma de salida. **/
	private Idioma idiomaSalida;

	/** Opciones **/
	private Opciones opciones;

	/**
	 * @return the contenidoDocumento
	 */
	public byte[] getContenidoDocumento() {
		return contenidoDocumento;
	}

	/**
	 * @param contenidoDocumento the contenidoDocumento to set
	 */
	public void setContenidoDocumento(final byte[] contenidoDocumento) {
		this.contenidoDocumento = contenidoDocumento;
	}

	/**
	 * @return the tipoDocumento
	 */
	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	/**
	 * @param tipoDocumento the tipoDocumento to set
	 */
	public void setTipoDocumento(final TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	/**
	 * @return the idiomaEntrada
	 */
	public Idioma getIdiomaEntrada() {
		return idiomaEntrada;
	}

	/**
	 * @param idiomaEntrada the idiomaEntrada to set
	 */
	public void setIdiomaEntrada(final Idioma idiomaEntrada) {
		this.idiomaEntrada = idiomaEntrada;
	}

	/**
	 * @return the idiomaSalida
	 */
	public Idioma getIdiomaSalida() {
		return idiomaSalida;
	}

	/**
	 * @param idiomaSalida the idiomaSalida to set
	 */
	public void setIdiomaSalida(final Idioma idiomaSalida) {
		this.idiomaSalida = idiomaSalida;
	}

	/**
	 * @return the opciones
	 */
	public Opciones getOpciones() {
		return opciones;
	}

	/**
	 * @param opciones the opciones to set
	 */
	public void setOpciones(final Opciones opciones) {
		this.opciones = opciones;
	}

}
