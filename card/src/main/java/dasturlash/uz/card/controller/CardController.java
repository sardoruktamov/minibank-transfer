package dasturlash.uz.card.controller;

import dasturlash.uz.card.dto.CardCreateDTO;
import dasturlash.uz.card.dto.CardDTO;
import dasturlash.uz.card.dto.StatusUpdateRequest;
import dasturlash.uz.card.service.CardMessageService;
import dasturlash.uz.card.service.CardService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/card")
@Slf4j
public class CardController {

    @Autowired
    private CardService cardService;
    @Autowired
    private CardMessageService cardMessageService;

    @PostMapping
    public ResponseEntity<CardDTO> createCard(@Valid @RequestBody CardCreateDTO createDTO) {
        try {
            CardDTO createdCard = cardService.createCard(createDTO);
            return new ResponseEntity<>(createdCard, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardDTO> getCardById(@PathVariable String id) {
        log.info("getCardById -> id = {} ", id);
        return cardService.getCardById(id)
                .map(card -> ResponseEntity.ok(card))
                .orElse(ResponseEntity.ok().build());
    }

    @GetMapping
    public ResponseEntity<List<CardDTO>> getAllCards() {
        List<CardDTO> cards = cardService.getAllCards();
        return ResponseEntity.ok(cards);
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<CardDTO>> getCardsByOwnerId(@PathVariable String ownerId) {
        List<CardDTO> cards = cardService.getCardsByOwnerId(ownerId);
        return ResponseEntity.ok(cards);
    }

    @GetMapping("/phone/{phoneNumber}")
    public ResponseEntity<List<CardDTO>> getCardsByPhoneNumber(@PathVariable String phoneNumber) {
        log.info("Get cards by phone number  = {}", phoneNumber);
        List<CardDTO> cards = cardService.getCardsByPhoneNumber(phoneNumber);
        return ResponseEntity.ok(cards);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CardDTO> updateCard(@PathVariable String id,
                                              @Valid @RequestBody CardCreateDTO updateDTO) {
        return cardService.updateCard(id, updateDTO)
                .map(card -> ResponseEntity.ok(card))
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<CardDTO> updateCardStatus(@PathVariable String id,
                                                    @RequestBody StatusUpdateRequest statusRequest) {
        return cardService.updateCardStatus(id, statusRequest.getStatus())
                .map(card -> ResponseEntity.ok(card))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable String id) {
        if (cardService.deleteCard(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/message")
    public String message() {
        return cardMessageService.getMessage();
    }

    @GetMapping("/message/{id}")
    public String message(@PathVariable("id") int id) {
        log.info(" Income request ");

        if (id == 1) {
            return cardMessageService.getMessage();
        } else {
            throw new RuntimeException("Exception from car service");
        }
    }
}