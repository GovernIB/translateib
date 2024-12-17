package es.caib.translatorib.ejb.facade;

import javax.annotation.security.PermitAll;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import es.caib.translatorib.ejb.interceptor.ExceptionTranslate;
import es.caib.translatorib.ejb.interceptor.Logged;
import es.caib.translatorib.persistence.repository.ProcesoRepository;

import es.caib.translatorib.service.service.SystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.translatorib.ejb.interceptor.NegocioInterceptor;
import es.caib.translatorib.ejb.service.component.ConfiguracionComponent;

/**
 * Servicio para verificar accesos de seguridad.
 *
 * @author Indra
 *
 */
@Logged
@ExceptionTranslate
@Stateless
@Local(SystemService.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class SystemServiceBean implements SystemService {

    /** Log. */
    private static Logger log = LoggerFactory
            .getLogger(SystemServiceBean.class);

    /** Configuracion. */
    @Inject
    private ConfiguracionComponent configuracion;

    /** Procesos DAO. */
    @Inject
    private ProcesoRepository procesosDAO;

    @Override
    @PermitAll
    @NegocioInterceptor
    public void purgar() {
        final String diasStr = configuracion
                .obtenerPropiedadConfiguracion("procesos.purga.dias");
        int dias = 365;
        if (diasStr != null && !diasStr.isEmpty()) {
            try {
                dias = Integer.parseInt(diasStr);
            } catch (final NumberFormatException nfe) {
                dias = 365;
            }
        }

    }

    @Override
    @NegocioInterceptor
    @PermitAll
    public boolean isMaestro(String instancia) {
        return procesosDAO.verificarMaestro(instancia);
    }


}
