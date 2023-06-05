package petmatch.api.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import petmatch.configuration.constance.Gender;
import petmatch.model.Breed;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResponse {
    @JsonProperty("id")
    private UUID profileId;
    private String avatar;
    private String name;
    private Breed breed;
    private Double height;
    private Double weight;
    private Gender gender;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "Asia/Bangkok")
    private Date birthday;
    private AddressResponse address;
}
