package hub.audit.domain.service;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "services")
@Data
@EqualsAndHashCode
@NoArgsConstructor
public class Service {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;
    private String apiKey;
}