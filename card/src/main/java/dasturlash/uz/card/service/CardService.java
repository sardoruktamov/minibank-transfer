package dasturlash.uz.card.service;

import dasturlash.uz.card.dto.CardCreateDTO;
import dasturlash.uz.card.dto.CardDTO;
import dasturlash.uz.card.dto.transaction.TransactionDTO;
import dasturlash.uz.card.entity.CardEntity;
import dasturlash.uz.card.enums.CardStatus;
import dasturlash.uz.card.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private TransactionFeignClient transactionFeignClient;
    public CardDTO createCard(CardCreateDTO createDTO) {
        CardEntity card = new CardEntity();
        card.setPhoneNumber(createDTO.getPhoneNumber());
        card.setAmount(createDTO.getAmount() != null ? createDTO.getAmount() : 0L);
        card.setOwnerId(createDTO.getOwnerId());
        card.setStatus(CardStatus.ACTIVE);
        card.setCardNumber(generateCardNumber());
        card.setCreatedAt(LocalDateTime.now());

        CardEntity savedCard = cardRepository.save(card);
        return convertToDTO(savedCard);
    }

    public Optional<CardDTO> getCardById(String id) {
        return cardRepository.findById(id)
                .map(this::convertToDTO);
    }

    public List<CardDTO> getAllCards() {
        return cardRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<CardDTO> getCardsByOwnerId(String ownerId) {
        return cardRepository.findByOwnerId(ownerId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<CardDTO> getCardsByPhoneNumber(String phoneNumber) {
        return cardRepository.findByPhoneNumber(phoneNumber)
                .stream()
                .map(cardEntity -> {
                    CardDTO dto = convertToDTO(cardEntity);
                    ResponseEntity<List<TransactionDTO>> response = transactionFeignClient.getTransactionsByCardId(cardEntity.getId());
                    dto.setTransaction(response.getBody());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public Optional<CardDTO> updateCard(String id, CardCreateDTO updateDTO) {
        return cardRepository.findById(id)
                .map(card -> {
                    card.setPhoneNumber(updateDTO.getPhoneNumber());
                    card.setAmount(updateDTO.getAmount() != null ? updateDTO.getAmount() : 0L);
                    card.setOwnerId(updateDTO.getOwnerId());
                    return convertToDTO(cardRepository.save(card));
                });
    }

    public Optional<CardDTO> updateCardStatus(String id, CardStatus status) {
        return cardRepository.findById(id)
                .map(card -> {
                    card.setStatus(status);
                    return convertToDTO(cardRepository.save(card));
                });
    }

    public boolean deleteCard(String id) {
        if (cardRepository.existsById(id)) {
            cardRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private CardDTO convertToDTO(CardEntity card) {
        CardDTO dto = new CardDTO();
        dto.setId(card.getId());
        dto.setPhoneNumber(card.getPhoneNumber());
        dto.setCardNumber(card.getCardNumber());
        dto.setAmount(card.getAmount());
        dto.setOwnerId(card.getOwnerId());
        dto.setStatus(card.getStatus());
        return dto;
    }

    private String generateCardNumber() {
        // Generate a 16-digit card number
        String cardNumber;
        do {
            cardNumber = String.format("%016d", Math.abs(UUID.randomUUID().getMostSignificantBits() % 10000000000000000L));
        } while (cardRepository.existsByCardNumber(cardNumber));
        return cardNumber;
    }
}
