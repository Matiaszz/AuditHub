package hub.audit.application;

import hub.audit.domain.user.User;
import hub.audit.domain.user.UserService;
import hub.audit.interfaces.dtos.requests.UserRequestDTO;
import hub.audit.interfaces.dtos.responses.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO data){
        User user = service.saveUserFromDTO(data);
        UserResponseDTO response = new UserResponseDTO(user);
        return ResponseEntity.status(201).body(response);
    }
}
