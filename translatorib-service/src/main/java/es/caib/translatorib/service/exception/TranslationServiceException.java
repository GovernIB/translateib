package es.caib.translatorib.service.exception;

import es.caib.translatorib.service.model.comun.ListaPropiedades;

import javax.persistence.PersistenceException;
import java.util.Date;

public class TranslationServiceException extends ServiceException {

    private static final long serialVersionUID = 1L;

    public TranslationServiceException(PersistenceException exception) {
        super(exception.getMessage());
    }
    public TranslationServiceException(String message) {
        super(message);
    }

    public TranslationServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Indica si es una excepcion de negocio o una excepcion no controlada.
     *
     * @return boolean
     */
    public boolean isNegocioException() {
        return false;
    }

    /**
     * Obtiene los detalles de la excepción.
     *
     * @return the detallesExcepcion
     */
    public ListaPropiedades getDetallesExcepcion() {
        return null;
    }

    /**
     * Obtiene fecha excepción.
     *
     * @return Fecha excepción
     */
    public Date getFechaExcepcion() {
        return null;
    }
}
