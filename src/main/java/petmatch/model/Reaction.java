package petmatch.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Reaction {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    @Nullable
    private String comment;
    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;
    @NotNull
    @Column(name = "created_ts")
    private Date createdTimestamp;
    @NotNull
    @Column(name = "updated_ts")
    private Date updatedTimestamp;
}
