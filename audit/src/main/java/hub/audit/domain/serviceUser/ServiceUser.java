package hub.audit.domain.serviceUser;

import hub.audit.domain.service.Service;
import hub.audit.domain.user.User;
import hub.audit.domain.user.UserRole;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class ServiceUser {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private Service service;

    @ManyToOne
    private User user;

    @Enumerated(EnumType.STRING)
    private ServiceRole role;
}
