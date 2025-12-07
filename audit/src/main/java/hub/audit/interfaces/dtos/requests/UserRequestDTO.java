package hub.audit.interfaces.dtos.requests;

import hub.audit.domain.user.UserRole;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
        @NotNull
        String firstName,
        @NotNull
        String lastName,
        @NotNull
        String email,

        @NotNull
        @Size(max = 6)
        String password
) {
}
