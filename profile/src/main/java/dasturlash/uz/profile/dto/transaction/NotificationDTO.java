package dasturlash.uz.profile.dto.transaction;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NotificationDTO {
    private String id;
    private String message;
    private String phoneNumber;
    private LocalDateTime createdDate;
}
