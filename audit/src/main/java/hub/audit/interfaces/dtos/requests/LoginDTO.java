package hub.audit.interfaces.dtos.requests;

import jakarta.validation.constraints.NotNull;

public record LoginDTO (
        @NotNull
        String email,

        @NotNull
        String password
){

}
