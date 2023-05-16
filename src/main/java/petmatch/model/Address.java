package petmatch.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    private String address;
    private Double longitude;
    private Double latitude;
    @Column(name = "geo_hash")
    private String geoHash;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
