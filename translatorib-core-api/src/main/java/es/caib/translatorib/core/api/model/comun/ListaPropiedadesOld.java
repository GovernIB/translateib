package es.caib.translatorib.core.api.model.comun;


import es.caib.translatorib.core.api.util.SerializadorMap;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Permite establecer una lista de propiedades mediante una lista de
 * propiedad/valor.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ListaPropiedadesOld implements Serializable {

    /**
     * Map con los detalles del error.
     */
    private final Map<String, String> propiedadesError = new LinkedHashMap<String, String>();

    /**
     * Añade un detalle de error.
     *
     * @param propiedad
     *            Propiedad
     * @param valor
     *            Parámetro valor
     */
    public void addPropiedad(final String propiedad, final String valor) {
        propiedadesError.put(propiedad, valor);
    }

    /**
     * Obtiene detalles del error como un Map<String,String>.
     *
     * @return Detalles error como un Map<String,String>
     */
    public Map<String, String> getPropiedades() {
        return this.propiedadesError;
    }

    /**
     * Método para Crea new lista propiedades de la clase ListaPropiedades.
     *
     * @return el lista propiedades
     */
    public static ListaPropiedadesOld createNewListaPropiedades() {
        return new ListaPropiedadesOld();
    }

    /**
     * Método para añadir nuevas propiedades a una ListaPropiedades pasándole otra
     * ListaPropiedades.
     *
     * @param lp
     *            Parámetro lp. ListaPropiedades a añadir
     */
    public void addPropiedades(final Map<String, String> lp) {
        if (lp != null) {
            for (final Map.Entry<String, String> propiedad : lp.entrySet()) {
                this.addPropiedad(propiedad.getKey(), propiedad.getValue());
            }
        }
    }

    /**
     * Método para añadir nuevas propiedades a una ListaPropiedades pasándole otra
     * ListaPropiedades.
     *
     * @param lp
     *            Parámetro lp. ListaPropiedades a añadir
     */
    public void addPropiedades(final ListaPropiedadesOld lp) {
        final Map<String, String> nueva = lp.getPropiedades();
        this.propiedadesError.putAll(nueva);
    }

    /**
     * Saca el contenido a una cadena.
     *
     * @return el atributo detalle
     */
    public String serializar() {
        return SerializadorMap.serializar(propiedadesError);
    }

    /**
     * Construye lista propiedades a partir de una cadena.
     *
     * @param cadena
     *            Cadena
     * @return lista propiedades
     */
    public static ListaPropiedadesOld deserializar(final String cadena) {
        ListaPropiedadesOld listaPropiedades = null;
        if (cadena != null) {
            listaPropiedades = new ListaPropiedadesOld();
            listaPropiedades.addPropiedades(SerializadorMap.deserializar(cadena));
        }
        return listaPropiedades;
    }

    /**
     * Serializa lista propiedades.
     *
     * @param pListaProps
     *            Lista propiedades
     * @return Cadena
     */
    public static String serializarListaPropiedades(final ListaPropiedadesOld pListaProps) {
        String res = null;
        if (pListaProps != null) {
            res = pListaProps.serializar();
        }
        return res;
    }

    /**
     * Obtiene valor propiedad.
     *
     * @param propiedad
     *            Nombre propiedad
     * @return valor propiedad
     */
    public String getPropiedad(final String propiedad) {
        return this.getPropiedades().get(propiedad);
    }
}

