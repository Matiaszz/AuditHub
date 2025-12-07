package hub.audit.domain.service;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Service {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;
    private String apiKey;
}