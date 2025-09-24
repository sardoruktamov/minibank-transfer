package dasturlash.uz.card.dto;

import dasturlash.uz.card.enums.CardStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardDTO {
    private String id;
    private String phoneNumber;
    private String cardNumber;
    private Long amount = 0l;
    private String ownerId; // profileId
    private CardStatus status;
}
