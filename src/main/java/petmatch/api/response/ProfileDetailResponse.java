package petmatch.api.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
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
    @JsonProperty("id")
    private UUID profileId;
    private String name;
    private Breed breed;
    private Double height;
    private String avatar;
    private Double weight;
    private Gender gender;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "Asia/Bangkok")
    private Date birthday;
    private List<String> gallery;
    private List<Breed> interests;
    private AddressResponse address;
}
