package dasturlash.uz.profile.service.fallback;

import dasturlash.uz.profile.dto.transaction.NotificationDTO;
import dasturlash.uz.profile.service.NotificationFeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collections;
import java.util.List;

@Component
public class NotificationFeignClientFallback implements NotificationFeignClient {
    @Override
    public ResponseEntity<List<NotificationDTO>> getNotificationsByPhoneNumber(@PathVariable String phoneNumber){
        return ResponseEntity.ok(Collections.emptyList());
    };
}
