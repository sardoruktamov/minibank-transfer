package dasturlash.uz.notification.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NotificationCreateDTO {
    @NotBlank(message = "Message required")
    private String message;
    @NotBlank(message = "PhoneNumber required")
    private String phoneNumber;
}
