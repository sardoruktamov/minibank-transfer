package dasturlash.uz.card.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CardMessageService {
    @Value("${accounts.message}")
    private String message;

    public String getMessage() {
        return message;
    }
}
