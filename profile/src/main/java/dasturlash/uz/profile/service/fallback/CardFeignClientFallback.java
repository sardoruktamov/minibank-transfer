package dasturlash.uz.profile.service.fallback;

import dasturlash.uz.profile.dto.card.CardDTO;
import dasturlash.uz.profile.service.CardFeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class CardFeignClientFallback implements CardFeignClient {
    @Override
    public ResponseEntity<List<CardDTO>> getCardsByPhoneNumber(String phoneNumber) {
        return ResponseEntity.ok(List.of());
    }
}
