package petmatch.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.checkerframework.common.aliasing.qual.Unique;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import petmatch.configuration.constance.Role;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity(name = "\"user\"")
@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails {
    @Id
    private String id; // From Firebase
    @Email(message = "Invalid email address")
    @NotNull
    @Length(min = 5, max = 50)
    private String email;
    @NotNull
    @Length(min = 3, max = 150)
    private String password;
    @Unique
    @Length(max = 15)
    private String phone;
    @OneToOne(mappedBy = "user")
    private Subscription subscription;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Profile> profiles;
    @NotNull
    @Column(name = "created_ts", updatable = false)
    @CreatedDate
    private Date createdTimestamp;
    @Column(name = "updated_ts")
    @LastModifiedDate
    private Date updatedTimestamp;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    @Builder.Default
    private Boolean isOnline = false;
    @NotNull
    private String fcmToken;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }


    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public @NotNull String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
