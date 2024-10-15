/**
 *
 */
package es.caib.translatorib.core.api.model.comun;

/**
 * @author Indra
 *
 */
public final class ConstantesRolesAcceso {

    private ConstantesRolesAcceso() {
        super();
    }

    public static final String SUPER_ADMIN = "TIB_ADMIN";

    public static final String CONSULTA = "TIB_CONS";

    public static final String API = "TIB_API";

    public static final String[] listaRoles() {
        final String[] rolesPrincipales = {SUPER_ADMIN, CONSULTA};
        return rolesPrincipales;
    }
}
