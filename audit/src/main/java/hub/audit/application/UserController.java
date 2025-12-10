package hub.audit.application;

import hub.audit.domain.user.User;
import hub.audit.domain.user.UserService;
import hub.audit.interfaces.dtos.requests.LoginDTO;
import hub.audit.interfaces.dtos.requests.UserRequestDTO;
import hub.audit.interfaces.dtos.responses.UserResponseDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Data
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserRequestDTO data){
        User user = service.saveUserFromDTO(data);
        UserResponseDTO response = new UserResponseDTO(user);
        return ResponseEntity.status(201).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody @Valid LoginDTO data, HttpServletResponse response) {

        String token = service.login(data);

        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 2);

        response.addCookie(cookie);
        response.addHeader(
                "Set-Cookie",
                "token=" + token +
                        "; Path=/; HttpOnly; Secure; SameSite=Strict; Max-Age=" + (60 * 60 * 24 * 2)
        );
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getMe(){
        User user = service.getCurrentUser();

        return user != null ? ResponseEntity.ok(
                new UserResponseDTO(user)) : ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);

        return ResponseEntity.noContent().build();
    }

}
