package es.caib.translatorib.plugin.api;

/**
 * Excepción al invocar la traduccion.
 *
 * @author Indra
 *
 */
public class TraduccionException extends Exception {

    private static final long serialVersionUID = 1L;

    public TraduccionException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public TraduccionException(String arg0) {
        super(arg0);
    }

    public TraduccionException(Throwable arg0) {
        super(arg0);
    }

}
