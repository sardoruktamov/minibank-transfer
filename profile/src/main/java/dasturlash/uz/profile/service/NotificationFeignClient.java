package dasturlash.uz.profile.service;

import dasturlash.uz.profile.dto.transaction.NotificationDTO;
import dasturlash.uz.profile.service.fallback.NotificationFeignClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "notification", fallback = NotificationFeignClientFallback.class)
public interface NotificationFeignClient {

    @GetMapping("/api/v1/notification/{phoneNumber}")
    ResponseEntity<List<NotificationDTO>> getNotificationsByPhoneNumber(@PathVariable String phoneNumber);
}
