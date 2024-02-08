package cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "players", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class PlayerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    @Column(nullable = false, updatable = false, insertable = false)
    @CreationTimestamp(source = SourceType.DB)
    private Timestamp registerDate;

    public PlayerEntity(String username) {
        this.username = username;
    }
}
