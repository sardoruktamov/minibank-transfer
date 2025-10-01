package dasturlash.uz.card.service.fallback;

import dasturlash.uz.card.dto.transaction.TransactionDTO;
import dasturlash.uz.card.service.TransactionFeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collections;
import java.util.List;

@Component
public class TransactionFeignClientFallback implements TransactionFeignClient {

    @Override
    public ResponseEntity<List<TransactionDTO>> getTransactionsByCardId(@PathVariable String cardId){
        return ResponseEntity.ok(Collections.emptyList());
    };
}
