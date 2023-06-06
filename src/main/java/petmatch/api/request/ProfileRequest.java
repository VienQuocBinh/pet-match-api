package petmatch.api.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import petmatch.configuration.constance.Gender;
import petmatch.model.Breed;
import petmatch.model.Interests;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ProfileRequest {
    @JsonProperty("id")
    private UUID profileId;
    @NotNull
    @JsonProperty("user-id")
    private String userId;
    @NotNull
    private Breed breed;
    @NotNull
    private String avatar;
    @NotNull
    private String name;
    private String description;
    @Positive
    @NotNull
    private Double height;
    @Positive
    @NotNull
    private Double weight;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date birthday;
    @Enumerated(EnumType.ORDINAL)
    @NotNull
    private Gender gender;
    private List<Breed> interests;
    private List<String> gallery;
    @NotNull
    private AddressRequest address;

}
