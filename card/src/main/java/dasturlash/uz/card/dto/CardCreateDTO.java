package dasturlash.uz.card.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardCreateDTO {
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid phone number format")
    private String phoneNumber;

    @Min(value = 0, message = "Amount must be non-negative")
    private Long amount = 0L;

    @NotBlank(message = "Owner ID is required")
    private String ownerId; // profileId
}
