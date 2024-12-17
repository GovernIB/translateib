package es.caib.translatorib.service.exception;

import es.caib.translatorib.service.model.comun.ListaPropiedades;

import java.util.Date;
import java.util.Locale;

/**
 * Excepció per indicar que el recurs sol·licitat en una operació no s'ha trobat.
 *
 * @author areus
 */
public class RecursNoTrobatException extends ServiceException {

    private static final long serialVersionUID = 1L;

    public RecursNoTrobatException() {
        super("Error, Recurso no encontrado");
        //Vacio
    }

    public String getLocalizedMessage(Locale locale) {
        return "Error, recurs no trobat";
    }

    @Override
    public boolean isNegocioException() {
        return false;
    }

    @Override
    public ListaPropiedades getDetallesExcepcion() {
        return null;
    }

    @Override
    public Date getFechaExcepcion() {
        return null;
    }
}

