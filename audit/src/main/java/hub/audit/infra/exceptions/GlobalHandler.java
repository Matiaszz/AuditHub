package hub.audit.infra.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiErrorResponse> handleResponseStatus(ResponseStatusException ex) {

        ApiErrorResponse err = new ApiErrorResponse(
                ex.getReason(),
                ex.getStatusCode().value(),
                ex.getStatusCode().toString(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(ex.getStatusCode()).body(err);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleDataIntegrity(DataIntegrityViolationException ex) {

        ApiErrorResponse err = new ApiErrorResponse(
                "Database constraint violation",
                400,
                "BAD_REQUEST",
                LocalDateTime.now()
        );

        return ResponseEntity.badRequest().body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .findFirst()
                .orElse("Invalid payload");

        ApiErrorResponse err = new ApiErrorResponse(
                message,
                400,
                "BAD_REQUEST",
                LocalDateTime.now()
        );

        return ResponseEntity.badRequest().body(err);
    }


}
