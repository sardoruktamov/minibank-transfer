package dasturlash.uz.transaction.service;

import dasturlash.uz.transaction.dto.TransactionCreateDTO;
import dasturlash.uz.transaction.dto.TransactionDTO;
import dasturlash.uz.transaction.entity.TransactionsEntity;
import dasturlash.uz.transaction.enums.TransactionStatusEnum;
import dasturlash.uz.transaction.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

//    @Autowired
//    private CardService cardService;

    public TransactionDTO createTransaction(TransactionCreateDTO createDTO) {
        // Validate cards exist
        validateCardExists(createDTO.getFromCardId());
        validateCardExists(createDTO.getToCardId());

        // Validate different cards
        if (createDTO.getFromCardId().equals(createDTO.getToCardId())) {
            throw new IllegalArgumentException("From card and to card cannot be the same");
        }

        // Validate amount
        if (createDTO.getAmount() == null || createDTO.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        TransactionsEntity transaction = new TransactionsEntity();
        transaction.setFromCardId(createDTO.getFromCardId());
        transaction.setToCardId(createDTO.getToCardId());
        transaction.setAmount(createDTO.getAmount());
        transaction.setTransactionStatus(TransactionStatusEnum.SUCCESS);
        transaction.setCreatedDate(LocalDateTime.now());
        // Card ->
        // Notification
        TransactionsEntity savedTransaction = transactionRepository.save(transaction);
        return convertToDTO(savedTransaction);
    }

    public Optional<TransactionDTO> getTransactionById(String id) {
//        throw new RuntimeException();
        return transactionRepository.findById(id)
                .map(this::convertToDTO);
    }

    public List<TransactionDTO> getAllTransactions() {
        return transactionRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<TransactionDTO> getTransactionsByFromCardId(String fromCardId) {
        return transactionRepository.findByFromCardId(fromCardId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<TransactionDTO> getTransactionsByToCardId(String toCardId) {
        return transactionRepository.findByToCardId(toCardId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<TransactionDTO> getTransactionsByCardId(String cardId) {
        return transactionRepository.findByCardId(cardId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<TransactionDTO> getTransactionsByStatus(TransactionStatusEnum status) {
        return transactionRepository.findByTransactionStatus(status)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<TransactionDTO> getTransactionsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return transactionRepository.findByDateRange(startDate, endDate)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<TransactionDTO> updateTransaction(String id, TransactionCreateDTO updateDTO) {
        return transactionRepository.findById(id)
                .map(transaction -> {
                    // Only allow updates if transaction is still pending
                    if (transaction.getTransactionStatus() != TransactionStatusEnum.SUCCESS) {
                        throw new IllegalStateException("Cannot update transaction with status: " +
                                transaction.getTransactionStatus());
                    }

                    // Validate cards exist
                    validateCardExists(updateDTO.getFromCardId());
                    validateCardExists(updateDTO.getToCardId());

                    // Validate different cards
                    if (updateDTO.getFromCardId().equals(updateDTO.getToCardId())) {
                        throw new IllegalArgumentException("From card and to card cannot be the same");
                    }

                    // Validate amount
                    if (updateDTO.getAmount() == null || updateDTO.getAmount() <= 0) {
                        throw new IllegalArgumentException("Amount must be positive");
                    }

                    transaction.setFromCardId(updateDTO.getFromCardId());
                    transaction.setToCardId(updateDTO.getToCardId());
                    transaction.setAmount(updateDTO.getAmount());

                    return convertToDTO(transactionRepository.save(transaction));
                });
    }

    public Optional<TransactionDTO> updateTransactionStatus(String id, TransactionStatusEnum status) {
        return transactionRepository.findById(id)
                .map(transaction -> {
                    transaction.setTransactionStatus(status);
                    return convertToDTO(transactionRepository.save(transaction));
                });
    }

    public boolean deleteTransaction(String id) {
        Optional<TransactionsEntity> transaction = transactionRepository.findById(id);
        if (transaction.isPresent()) {
            // Only allow deletion if transaction is pending or failed
            TransactionStatusEnum status = transaction.get().getTransactionStatus();
            if (status == TransactionStatusEnum.FAILED) {
                transactionRepository.deleteById(id);
                return true;
            } else {
                throw new IllegalStateException("Cannot delete transaction with status: " + status);
            }
        }
        return false;
    }

    private TransactionDTO convertToDTO(TransactionsEntity transaction) {
        TransactionDTO dto = new TransactionDTO();
        dto.setId(transaction.getId());
        dto.setFromCardId(transaction.getFromCardId());
        dto.setToCardId(transaction.getToCardId());
        dto.setAmount(transaction.getAmount());
        dto.setTransactionStatus(transaction.getTransactionStatus());
        dto.setCreatedDate(transaction.getCreatedDate());
        return dto;
    }

    private void validateCardExists(String cardId) {
//        if (cardService.getCardById(cardId).isEmpty()) {
//            throw new IllegalArgumentException("Card not found with ID: " + cardId);
//        }
        // TODO
    }
}