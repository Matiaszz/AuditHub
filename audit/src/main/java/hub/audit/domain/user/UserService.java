package hub.audit.domain.user;

import hub.audit.infra.jwt.Jwt;
import hub.audit.interfaces.ValueObjects.FindBy;
import hub.audit.interfaces.dtos.requests.LoginDTO;
import hub.audit.interfaces.dtos.requests.SetAdminDTO;
import hub.audit.interfaces.dtos.requests.UserRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private Jwt jwt;


    @Cacheable(value = "userByEmail", key = "#key", condition = "#findBy.name() == 'EMAIL'")
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

        User user = new User(dto, password, UserRole.COMMON);
        return repository.save(user);
    }

    public User saveAdminFromDTO(SetAdminDTO dto){
        adminCheck();
        String formattedEmail = dto.email().trim().toLowerCase();
        User userExist = repository.findByEmail(formattedEmail).orElse(null);

        if (userExist == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Email doesn't exist");
        }

        userExist.setRole(UserRole.ADMIN);
        return repository.save(userExist);
    }

    public String login(LoginDTO data){
        System.out.println("Chegou no login service");
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        System.out.println("Chegou no usernamepassword");
        Authentication auth;
        try{
            auth = this.authenticationManager.authenticate(usernamePassword);
        } catch(BadCredentialsException e){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Credenciais invalidas");
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

        System.out.println("Chegou no authenticate");

        if (!auth.isAuthenticated()){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid credentials.");
        }

        if (auth.getPrincipal() == null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You cannot signin.");
        }

        String token = jwt.generateToken((User) auth.getPrincipal());
        SecurityContextHolder.getContext().setAuthentication(auth);
        return token;
    }

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || Objects.equals(auth.getPrincipal(), "anonymousUser")) {
            return null;
        }

        return (User) auth.getPrincipal();
    }

    public void adminCheck(){
        User loggedUser = getCurrentUser();
        if (!loggedUser.isAdmin()){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You aren't authorized to do this action.");
        }
    }
}
