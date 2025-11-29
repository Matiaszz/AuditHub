package hub.audit.interfaces.dtos.responses;

import hub.audit.domain.user.User;
import hub.audit.domain.user.UserRole;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String firstName,
        String lastName,
        String email,
        Boolean isEnabled,
        UserRole role,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public UserResponseDTO(User user){
        this(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.isEnabled(),
                user.getRole(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
