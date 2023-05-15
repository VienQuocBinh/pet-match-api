package petmatch.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "profile_gallery", schema = "public", catalog = "pet-match")
public class ProfileGallery {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id")
    private Profile profile;
    @Column(name = "gallery")
    private String gallery;
    @NotNull
    @Column(name = "created_ts")
    private Date createTimestamp;
    @NotNull
    @Column(name = "updated_ts")
    private Date updatedTimestamp;
}
