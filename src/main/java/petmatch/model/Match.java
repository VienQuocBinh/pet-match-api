package petmatch.model;

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
@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
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
    @CreatedDate
    private Date createdTimestamp;
    @NotNull
    @Column(name = "updated_ts")
    @LastModifiedDate
    private Date updatedTimestamp;
}
