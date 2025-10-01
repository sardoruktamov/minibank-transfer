package dasturlash.uz.card.service;

import dasturlash.uz.card.dto.transaction.TransactionDTO;
import dasturlash.uz.card.service.fallback.TransactionFeignClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "transaction", fallback = TransactionFeignClientFallback.class)
public interface TransactionFeignClient {

    @GetMapping("/api/v1/transaction/card/{cardId}")
    ResponseEntity<List<TransactionDTO>> getTransactionsByCardId(@PathVariable String cardId);
}
