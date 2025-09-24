package dasturlash.uz.transaction.dto;

import dasturlash.uz.transaction.enums.TransactionStatusEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionStatusUpdateRequest {
    private TransactionStatusEnum status;
}

