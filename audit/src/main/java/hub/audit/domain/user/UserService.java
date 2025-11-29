package hub.audit.domain.user;

import hub.audit.interfaces.ValueObjects.FindBy;
import hub.audit.interfaces.dtos.requests.UserRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User getUser(String key, FindBy findBy){
        if (key == null || key.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Key is empty, is not possible to find an User.");
        }

        if (findBy == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "findBy Param is empty, is not possible to find an User.");
        }

        return switch (findBy) {

            case ID -> {
                UUID id;

                try {
                    id = UUID.fromString(key);
                } catch (IllegalArgumentException e) {
                    throw new ResponseStatusException(
                            HttpStatus.UNPROCESSABLE_CONTENT,
                            "Invalid UUID format."
                    );
                }

                yield this.repository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User with this ID was not found."
                        ));
            }

            case EMAIL -> this.repository.findByEmail(key)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "User with this email was not found."
                    ));
        };

    }


    public User saveUserFromDTO(UserRequestDTO dto){
        String formattedEmail = dto.email().trim().toLowerCase();
        boolean userExist = repository.existsByEmail(formattedEmail);

        if (userExist){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exist");
        }

        String password = passwordEncoder.encode(dto.password().trim());
        UserRole userRole = getUserRoleFromDTO(dto);

        User user = new User(dto,password, userRole);
        return repository.save(user);
    }



    private UserRole getUserRoleFromDTO(UserRequestDTO dto) {
        if (dto.role() == null || dto.role().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role is null");
        }

        String role = dto.role().trim().toUpperCase();

        if (role.startsWith("ROLE_")){
            role = role.substring(5);
        }

        UserRole userRole;
        switch (role){
            case "ADMIN" -> userRole = UserRole.ADMIN;
            case "DEVOPS" -> userRole = UserRole.DEVOPS;
            case "DEV" -> userRole = UserRole.DEV;
            case "AUDITOR" -> userRole = UserRole.AUDITOR;
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid role");
        }
        return userRole;
    }
}
