package petmatch.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.checkerframework.checker.units.qual.C;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Match {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "match_from")
    private Profile matchFrom;
    @ManyToOne
    @JoinColumn(name = "match_to")
    private Profile matchTo;
    @NotNull
    @Column(name = "created_ts")
    private Date createdTimestamp;
    @NotNull
    @Column(name = "updated_ts")
    private Date updatedTimestamp;
}
