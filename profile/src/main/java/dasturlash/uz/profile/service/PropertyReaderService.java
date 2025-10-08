package dasturlash.uz.profile.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

@RefreshScope
@Service
public class PropertyReaderService {
    @Value("${profile.message}")
    private String message;

    public String getMessage() {
        return message;
    }
}
