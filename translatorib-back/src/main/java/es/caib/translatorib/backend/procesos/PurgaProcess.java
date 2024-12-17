package es.caib.translatorib.backend.procesos;

import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import es.caib.translatorib.service.service.SystemService;
import es.caib.translatorib.service.util.GeneradorId;

/**
 * Proceso que arranca proceso purga ficheros.
 */
@Component
public final class PurgaProcess {

    /** Id Servlet Context (instancia aplicacion). */
    public static final String SERVLET_CONTEXT_ID = "sistramit-SERVLET_CONTEXT_ID";

    /** Log. */
    private static Logger log = LoggerFactory.getLogger(PurgaProcess.class);

    //@Inject
    private SystemService systemService;

    //@Inject
    private ServletContext servletContext;

    /**
     * Process.
    @Scheduled(cron = "${procesos.purga.cron}")
     */
    public void process() {
        log.debug("Proceso purgarFicheros");
        final String instancia = getIdServletContext();
        if (StringUtils.isNotBlank(instancia)) {
            if (systemService.isMaestro(instancia)) {
                log.debug("Es maestro. Lanza purga");
                systemService.purgar();
            } else {
                log.debug("No es maestro. No lanza purga");
            }
        } else {
            log.warn("No se ha podido obtener id instancia.");
        }
    }

    /**
     * Obtiene id instancia.
     *
     * @return id instancia
     */
    private String getIdServletContext() {
        String id = null;
        if (servletContext != null) {
            id = (String) servletContext.getAttribute(SERVLET_CONTEXT_ID);
            if (id == null) {
                id = GeneradorId.generarId();
                servletContext.setAttribute(SERVLET_CONTEXT_ID, id);
            }
        }
        return id;
    }

}