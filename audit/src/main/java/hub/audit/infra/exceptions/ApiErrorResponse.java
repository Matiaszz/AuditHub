package hub.audit.infra.exceptions;

import java.time.LocalDateTime;

public record ApiErrorResponse(
        String message,
        int status,
        String error,
        LocalDateTime timestamp
) {}