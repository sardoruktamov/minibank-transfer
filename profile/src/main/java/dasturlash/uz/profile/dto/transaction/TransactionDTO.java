package dasturlash.uz.profile.dto.transaction;

import dasturlash.uz.profile.enums.TransactionStatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionDTO {
    private String id;
    private String fromCardId;
    private String toCardId;
    private Long amount;
    private TransactionStatusEnum transactionStatus;
    private LocalDateTime createdDate;
}
