package petmatch.api.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import petmatch.configuration.constance.Gender;
import petmatch.model.Breed;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDetailResponse {
    private UUID profileId;
    private String name;
    private Breed type;

    private Double height;
    private Double weight;
    private Gender gender;
    private String description;
    private List<Breed> interest;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date birthday;
    private List<String> gallery;
}
