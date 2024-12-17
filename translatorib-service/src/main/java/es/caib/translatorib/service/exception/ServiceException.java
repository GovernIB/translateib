package es.caib.translatorib.service.exception;

import java.util.Date;

import es.caib.translatorib.service.model.comun.ListaPropiedades;

public abstract class ServiceException extends Exception {

    private static final long serialVersionUID = 1L;

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Indica si es una excepcion de negocio o una excepcion no controlada.
     *
     * @return boolean
     */
    public abstract boolean isNegocioException();

    /**
     * Obtiene los detalles de la excepción.
     *
     * @return the detallesExcepcion
     */
    public abstract ListaPropiedades getDetallesExcepcion();

    /**
     * Obtiene fecha excepción.
     *
     * @return Fecha excepción
     */
    public abstract Date getFechaExcepcion();
}
