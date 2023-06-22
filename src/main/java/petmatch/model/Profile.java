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
    @OneToMany(mappedBy = "profile", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<Gallery> gallery;
    @NotNull
    @Column(name = "avatar")
    private String avatar;
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
    @OneToMany(mappedBy = "profile", fetch = FetchType.LAZY)
    private List<Reaction> reaction;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "breed_id")
    private Breed breed;
    @OneToMany(mappedBy = "matchFrom", fetch = FetchType.LAZY)
    private List<Match> matchFrom;
    @OneToMany(mappedBy = "matchTo", fetch = FetchType.LAZY)
    private List<Match> matchTo;
    @OneToMany(mappedBy = "profile", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<Interests> interests;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;
    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Reaction> reactedList;
}
