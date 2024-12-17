package es.caib.translatorib.persistence.repository;

import es.caib.translatorib.persistence.model.JProceso;
import es.caib.translatorib.service.exception.ProcesoConfiguracionException;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.Date;

/**
 * Proceso DAO.
 */
@Stateless
@Local(ProcesoRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class ProcesoRepositoryBean extends AbstractCrudRepository<JProceso, Long> implements ProcesoRepository {

    /** Tiempo máximo inactivo que puede estar el maestro. */
    private static final int MINUTOS_MAX_INACTIVO = 15;

    /** Id maestro. */
    private static final String ID_MAESTRO = "MAESTRO";

    /**
     * Constructor
     */
    protected ProcesoRepositoryBean() {
        super(JProceso.class);
    }


    @Override
    public boolean verificarMaestro(final String instanciaId) {

        boolean res = false;
        boolean tomarControl = false;
        final Date fechaActual = new Date();

        // Recuperamos info actual (debe existir siempre)
        final JProceso jProceso = entityManager.find(JProceso.class,
                ID_MAESTRO);
        if (jProceso == null) {
            throw new ProcesoConfiguracionException(
                    "No existe fila en tabla de procesos con id " + ID_MAESTRO);
        }

        /*
        // Verifica si esta configurado como maestro
        // - Instancia actual
        if (jProceso.getInstancia().equals(instanciaId)) {
            // Intentamos renovar
            tomarControl = true;
        } else {
            // Si se ha sobrepasado el tiempo sin que la instancia configurada
            // como maestro
            // este activa, se intenta tomar control
            final Calendar cal = Calendar.getInstance();
            cal.setTime(jProceso.getFecha());
            cal.add(Calendar.MINUTE, MINUTOS_MAX_INACTIVO);
            final Date fechaLimite = cal.getTime();
            tomarControl = fechaLimite.before(fechaActual);
        }

        // Toma de control
        if (tomarControl) {
            final String sql = "UPDATE JProceso p SET p.instancia = :instanciaActual, p.fecha = :fechaActual WHERE p.fecha = :fechaOld AND p.instancia = :instanciaOld";
            final Query query = entityManager.createQuery(sql);
            query.setParameter("instanciaActual", instanciaId);
            query.setParameter("instanciaOld", jProceso.getInstancia());
            query.setParameter("fechaActual", fechaActual);
            query.setParameter("fechaOld", jProceso.getFecha());
            final int update = query.executeUpdate();
            res = (update == 1);
        }
        */
        return res;
    }

}
