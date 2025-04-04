package guru_springframework.spring_6_rest_mvc.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    private UUID id;
    private String customerName;

    @Version
    private Integer version;

    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
