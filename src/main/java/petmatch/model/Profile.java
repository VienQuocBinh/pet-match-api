package petmatch.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    @NotNull
    @Length(min = 3, max = 100)
    private String name;
    private Date birthDate;
    private String gender; // enum gender
    private String description;
    private String attribute;
    @ElementCollection
    private List<String> gallery;
    @ElementCollection
    private List<String> interests;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "profile")
    private List<Reaction> reaction;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bread_id")
    private Breed breed;

    @OneToMany(mappedBy = "profile")
    private List<Match> match;
}
