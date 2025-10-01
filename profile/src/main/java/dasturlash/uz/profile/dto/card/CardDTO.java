package dasturlash.uz.profile.dto.card;

import dasturlash.uz.profile.dto.transaction.TransactionDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CardDTO {
    private String id;
    private String phoneNumber;
    private String cardNumber;
    private Long amount = 0l;
    private String ownerId; // profileId
    private CardStatus status;

    private List<TransactionDTO> transaction;
}
