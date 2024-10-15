package es.caib.translatorib.core.api.model.types;

public enum TypeRoleAcceso {
    SUPER_ADMIN("TIB_ADMIN");

    /** Rol **/
    private String role;

    private TypeRoleAcceso(String role) {
        this.role = role;
    }

    public static TypeRoleAcceso fromString(String role) {
        if (role != null) {
            for (TypeRoleAcceso typeRoleAcceso : TypeRoleAcceso.values()) {
                if (role.equalsIgnoreCase(typeRoleAcceso.name())) {
                    return typeRoleAcceso;
                }
            }
        }
        return null;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return role;
    }
}
