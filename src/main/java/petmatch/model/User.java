package petmatch.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.checkerframework.checker.units.qual.N;
import org.checkerframework.common.aliasing.qual.Unique;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;

@Entity(name = "\"user\"")
@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String id; // From Firebase
    @Email(message = "Invalid email address")
    @NotNull
    @Length(min = 5, max = 40)
    private String email;
    @NotNull
    @Length(min = 3, max = 40)
    private String password;
    @Unique
    @Length(min = 10)
    private String phone;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Subscription subscription;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Profile> profiles;
    @NotNull
    @Column(name = "created_ts")
    private Date createdTimestamp;
    @NotNull
    @Column(name = "updated_ts")
    private Date updatedTimestamp;
}
