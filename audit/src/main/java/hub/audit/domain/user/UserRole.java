package hub.audit.domain.user;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    COMMON("ROLE_ADMIN");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public List<String> getPermissions() {
        return switch (this) {
            case ADMIN -> List.of("ROLE_ADMIN", "ROLE_COMMON");
            case COMMON -> List.of("ROLE_COMMON");
        };
    }
}
