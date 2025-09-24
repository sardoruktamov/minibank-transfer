package dasturlash.uz.card.dto;

import dasturlash.uz.card.enums.CardStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusUpdateRequest {
    private CardStatus status;
}