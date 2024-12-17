package es.caib.translatorib.service.model.comun;


/**
 *
 * Clase propiedad básica de codigo por valor.
 *
 * @author Indra
 *
 */

public final class Propiedad {

	/**
	 * Código.
	 */
	private String codigo;

	/**
	 * Valor
	 */
	private String valor;

	/**
	 * Orden.
	 */
	private Integer orden;

	/**
	 * Instancia una nueva Propiedad.
	 */
	public Propiedad() {
	}

	/**
	 * Instancia una nueva Propiedad.
	 *
	 * @param otro the otro
	 */
	public Propiedad(Propiedad otro) {
		if (otro != null) {
			this.codigo = otro.codigo;
			this.valor = otro.valor;
			this.orden = otro.orden;
		}
	}

	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            the codigo to set
	 */
	public void setCodigo(final String codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @param valor
	 *            the valor to set
	 */
	public void setValor(final String valor) {
		this.valor = valor;
	}

	/**
	 * @return the orden
	 */
	public Integer getOrden() {
		return orden;
	}

	/**
	 * @param orden
	 *            the orden to set
	 */
	public void setOrden(final Integer orden) {
		this.orden = orden;
	}

	@Override
	public String toString() {
        return toString("","ca");
	}

	/**
     * Método to string
     * @param tabulacion Indica el texto anterior de la linea para que haya tabulacion.
     * @return El texto
     */
     public String toString(String tabulacion, String idioma) {
           StringBuilder texto = new StringBuilder(tabulacion); texto.append("Propietat. \n");
           texto.append(tabulacion); texto.append("\t Codi:"); texto.append(codigo); texto.append("\n");
           texto.append(tabulacion); texto.append("\t Valor:"); texto.append(valor); texto.append("\n");
           texto.append(tabulacion); texto.append("\t Ordre:"); texto.append(orden); texto.append("\n");
           return texto.toString();
     }



	@Override
	public Propiedad clone() {
         return new Propiedad(this);
	}

}
