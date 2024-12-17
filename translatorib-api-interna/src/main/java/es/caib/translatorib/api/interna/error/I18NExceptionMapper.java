package es.caib.translatorib.api.interna.error;

import es.caib.translatorib.api.interna.config.ApiConstants;
import es.caib.translatorib.commons.i18n.I18NException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Locale;

/**
 * Permet mapejar a una resposta comuna a les excepcions de tipus I18NException.
 * Són bàsicament excepcions a la lògica de l'aplicació, i per tant enviarem un error
 * 400 al client.
 *
 * @author Indra
 */
@Provider
public class I18NExceptionMapper implements ExceptionMapper<I18NException> {

    private static final Logger LOG = LoggerFactory.getLogger(I18NExceptionMapper.class);

    @Context
    private HttpServletRequest request;

    @Override
    public Response toResponse(I18NException e) {

        Locale locale = (Locale) request.getAttribute(ApiConstants.REQUEST_LOCALE);
        String message = e.getLocalizedMessage(locale);
        LOG.error("Rebuda una I18NException: " + message);

        ErrorBean errorBean = new ErrorBean();
        errorBean.setType(ErrorType.APLICACIO);
        errorBean.setMessage(message);

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errorBean)
                .build();
    }
}
