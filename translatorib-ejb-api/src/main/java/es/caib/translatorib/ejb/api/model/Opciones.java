package es.caib.translatorib.ejb.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "Opciones")
public class Opciones implements Serializable {

	/** Serial Version UID */
	private static final long serialVersionUID = 1L;

	/** Opciones en OPENTRAD **/
	/**
	 * Indica si se quiere usar detección de entidades o no. Si se decide utilizar,
	 * los nombres propios que el sistema detecte como tales quedarán sin traducir.
	 */
	public static final String OPENTRAD_NER = "NER";
	public static final String OPENTRAD_NER_VALOR_ACTIVO = "true";
	public static final String OPENTRAD_NER_VALOR_INACTIVO = "false";
	/**
	 * Marcador de palabras desconocidas. En caso de que no se haya encontrado una
	 * traducción adecuada a una palabra esta quedará marcada por defecto con un
	 * asterisco (*)
	 **/
	public static final String OPENTRAD_MARKUNKNOWN = "NER";
	public static final String OPENTRAD_MARKUNKNOWN_VALOR_ACTIVO = "-u";
	public static final String OPENTRAD_MARKUNKNOWN_VALOR_INACTIVO = "";

	/** Campos **/
	private List<PropiedadValor> propiedades = new ArrayList<PropiedadValor>();

	public List<PropiedadValor> getPropiedades() {
		return propiedades;
	}

	public void setPropiedades(final List<PropiedadValor> propiedades) {
		this.propiedades = propiedades;
	}

	public void addPropiedadValor(final String prop, final String val) {
		if (propiedades == null) {
			propiedades = new ArrayList<PropiedadValor>();
		}
		final PropiedadValor propiedad = new PropiedadValor();
		propiedad.setPropiedad(prop);
		propiedad.setValor(val);
		propiedades.add(propiedad);

	}

	public boolean contains(final String nombrePropiedad) {
		boolean contiene = false;
		if (propiedades != null) {
			for (final PropiedadValor prop : propiedades) {
				if (prop.getPropiedad() != null && prop.getPropiedad().equals(nombrePropiedad)) {
					contiene = true;
					break;
				}
			}
		}

		return contiene;
	}

	public String getValor(final String nombrePropiedad) {
		String valor = null;
		if (propiedades != null) {
			for (final PropiedadValor prop : propiedades) {
				if (prop.getPropiedad().equals(nombrePropiedad)) {
					valor = prop.getValor();
					break;
				}
			}
		}

		return valor;
	}

}
