package dasturlash.uz.profile.service;

import dasturlash.uz.profile.dto.card.CardDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "card")
public interface CardFeignClient {

    @GetMapping("/api/v1/card/phone/{phoneNumber}")
    ResponseEntity<List<CardDTO>> getCardsByPhoneNumber(@PathVariable String phoneNumber);
}
