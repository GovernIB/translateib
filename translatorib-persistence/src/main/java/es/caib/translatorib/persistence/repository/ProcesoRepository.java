package es.caib.translatorib.persistence.repository;

public interface ProcesoRepository {

    /**
     * Verifica si la instancia actual es maestro. En caso de que no haya
     * maestro o se haya cumplido el plazo, se intenta establecer como maestro.
     *
     * @param instanciaId
     *            id instancia
     * @return true si es maestro
     */
    boolean verificarMaestro(String instanciaId);

}