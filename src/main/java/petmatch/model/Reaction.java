package petmatch.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
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
    @CreatedDate
    private Date createdTimestamp;
    @NotNull
    @Column(name = "updated_ts")
    @LastModifiedDate
    private Date updatedTimestamp;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private Profile createdBy;
}
