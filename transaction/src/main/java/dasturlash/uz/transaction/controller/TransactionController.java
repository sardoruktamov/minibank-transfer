package dasturlash.uz.transaction.controller;

import dasturlash.uz.transaction.service.TransactionService;
import dasturlash.uz.transaction.dto.TransactionCreateDTO;
import dasturlash.uz.transaction.dto.TransactionDTO;
import dasturlash.uz.transaction.dto.TransactionStatusUpdateRequest;
import dasturlash.uz.transaction.enums.TransactionStatusEnum;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    @Value("${accounts.message}")
    private String message;

    @PostMapping
    public ResponseEntity<TransactionDTO> createTransaction(@Valid @RequestBody TransactionCreateDTO createDTO) {
        try {
            TransactionDTO createdTransaction = transactionService.createTransaction(createDTO);
            return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable String id) {
        log.info("getTransactionById -> id = {} ", id);
        return transactionService.getTransactionById(id)
                .map(transaction -> ResponseEntity.ok(transaction))
                .orElse(ResponseEntity.ok().build());
    }

    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getAllTransactions() {
        List<TransactionDTO> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/from-card/{fromCardId}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByFromCardId(@PathVariable String fromCardId) {
        List<TransactionDTO> transactions = transactionService.getTransactionsByFromCardId(fromCardId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/to-card/{toCardId}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByToCardId(@PathVariable String toCardId) {
        List<TransactionDTO> transactions = transactionService.getTransactionsByToCardId(toCardId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/card/{cardId}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByCardId(@PathVariable String cardId) {
        List<TransactionDTO> transactions = transactionService.getTransactionsByCardId(cardId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByStatus(@PathVariable TransactionStatusEnum status) {
        List<TransactionDTO> transactions = transactionService.getTransactionsByStatus(status);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<TransactionDTO> transactions = transactionService.getTransactionsByDateRange(startDate, endDate);
        return ResponseEntity.ok(transactions);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionDTO> updateTransaction(@PathVariable String id,
                                                            @Valid @RequestBody TransactionCreateDTO updateDTO) {
        try {
            return transactionService.updateTransaction(id, updateDTO)
                    .map(transaction -> ResponseEntity.ok(transaction))
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalStateException | IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TransactionDTO> updateTransactionStatus(@PathVariable String id,
                                                                  @RequestBody TransactionStatusUpdateRequest statusRequest) {
        return transactionService.updateTransactionStatus(id, statusRequest.getStatus())
                .map(transaction -> ResponseEntity.ok(transaction))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable String id) {
        try {
            if (transactionService.deleteTransaction(id)) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/message")
    public String getMessage() {
        return message;
    }
}
