package hub.audit.interfaces.dtos.requests;

import hub.audit.domain.user.UserRole;

public record UserRequestDTO(
        String firstName,
        String lastName,
        String email,
        String password,
        String role
) {
}
