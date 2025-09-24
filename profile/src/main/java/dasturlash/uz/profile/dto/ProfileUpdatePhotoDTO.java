package dasturlash.uz.profile.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileUpdatePhotoDTO {

    @NotBlank(message = "PhotoId required")
    private String photoId;
}
