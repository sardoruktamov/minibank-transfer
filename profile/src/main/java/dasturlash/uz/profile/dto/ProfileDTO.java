package dasturlash.uz.profile.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import dasturlash.uz.profile.enums.ProfileRoleEnum;
import dasturlash.uz.profile.enums.ProfileStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO {
    private String id;

    @NotBlank(message = "Ism bo‘sh bo‘lmasligi kerak")
    private String name;

    @NotBlank(message = "Familiya bo‘sh bo‘lmasligi kerak")
    private String surname;

    @NotBlank(message = "PhoneNumber bo‘sh bo‘lmasligi kerak")
    private String phoneNumber;

    @NotBlank(message = "Parol bo‘sh bo‘lmasligi kerak")
    private String password;

    @NotEmpty(message = "Role bo‘sh bo‘lmasligi kerak")
    private List<ProfileRoleEnum> roleList;

    private LocalDateTime createdDate;
    private ProfileStatus status;
    private String jwt;
}
