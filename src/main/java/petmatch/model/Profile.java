package petmatch.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import petmatch.configuration.constance.Gender;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Profile {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    @NotNull
    @Length(min = 3, max = 100)
    private String name;
    private Double height;
    private Double weight;
    private Date birthday;
    @Enumerated(EnumType.STRING)
    private Gender gender; // enum gender
    private String description;
    @ElementCollection
    private List<String> gallery;
    @ElementCollection
    private List<String> interests;

    @NotNull
    @Column(name = "created_ts")
    @CreatedDate
    private Date createdTimestamp;
    @NotNull
    @Column(name = "updated_ts")
    @LastModifiedDate
    private Date updatedTimestamp;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "profile")
    private List<Reaction> reaction;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "breed_id")
    private Breed breed;
    @OneToMany(mappedBy = "matchFrom")
    private List<Match> matchFrom;
    @OneToMany(mappedBy = "matchTo")
    private List<Match> matchTo;
}
