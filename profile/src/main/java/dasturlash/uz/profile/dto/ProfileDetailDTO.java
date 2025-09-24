package dasturlash.uz.profile.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import dasturlash.uz.profile.dto.card.CardDTO;
import dasturlash.uz.profile.dto.transaction.NotificationDTO;
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
public class ProfileDetailDTO {
    private String id;

    private String name;
    private String surname;
    private String phoneNumber;
    private List<CardDTO> cardList;
    private List<NotificationDTO> notificationList;
}
