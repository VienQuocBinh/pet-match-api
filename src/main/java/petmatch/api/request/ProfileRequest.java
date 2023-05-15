package petmatch.api.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import petmatch.configuration.constance.Gender;
import petmatch.model.Breed;

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
    @NotNull
    private Breed breed;
    @NotNull
    private String avatar;
    @NotNull
    private String name;
    @Positive
    @NotNull
    private Double height;
    @Positive
    @NotNull
    private Double weight;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date birthday;
    @Enumerated(EnumType.ORDINAL)
    private Gender gender;
    private List<Breed> interests;
}
