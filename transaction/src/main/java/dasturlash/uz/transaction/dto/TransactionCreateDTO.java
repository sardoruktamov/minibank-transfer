package dasturlash.uz.transaction.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionCreateDTO {
    @NotBlank(message = "From card ID is required")
    private String fromCardId;

    @NotBlank(message = "To card ID is required")
    private String toCardId;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private Long amount;
}
