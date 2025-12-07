package hub.audit.domain.serviceUser;

import java.util.List;

public enum ServiceRole {
    ADMIN("ROLE_ADMIN"),
    DEVOPS("ROLE_DEVOPS"),
    DEV("ROLE_DEV"),
    AUDITOR("ROLE_AUDITOR");

    private String role;

    ServiceRole(String role) {
        this.role = role;
    }

    public List<String> getPermissions(){
        return switch (this) {
            case ADMIN -> List.of("ROLE_ADMIN", "ROLE_DEVOPS", "ROLE_DEV", "ROLE_AUDITOR");
            case DEVOPS -> List.of( "ROLE_DEVOPS", "ROLE_DEV", "ROLE_AUDITOR");
            case DEV -> List.of("ROLE_DEV", "ROLE_AUDITOR");
            case AUDITOR -> List.of("ROLE_AUDITOR");
        };
    }
}
