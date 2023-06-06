package petmatch.api.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReactionResponse {
    private String comment;
    @NotNull
    private ProfileDetailResponse profile;
    @NotNull
    @JsonProperty("created-at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "Asia/Bangkok")
    private Date createdTimestamp;
    @NotNull
    @JsonProperty("updated-at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "Asia/Bangkok")
    private Date updatedTimestamp;
}
