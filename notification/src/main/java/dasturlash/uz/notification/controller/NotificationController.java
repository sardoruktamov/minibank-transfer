package dasturlash.uz.notification.controller;

import dasturlash.uz.notification.dto.NotificationCreateDTO;
import dasturlash.uz.notification.dto.NotificationDTO;
import dasturlash.uz.notification.service.NotificationService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;
    @Value("${accounts.message}")
    private String message;


    @PostMapping
    public ResponseEntity<NotificationDTO> createNotification(@Valid @RequestBody NotificationCreateDTO createDTO) {
        NotificationDTO createdNotification = notificationService.createNotification(createDTO);
        return new ResponseEntity<>(createdNotification, HttpStatus.CREATED);
    }

    @GetMapping("/{phoneNumber}")
    public ResponseEntity<List<NotificationDTO>> getNotificationsByPhoneNumber(@RequestHeader("correlation-id") String correlationId,
                                                                               @PathVariable String phoneNumber) {
        log.info("Get notifications by to account toAccount = {}, correlationId = {} ", phoneNumber, correlationId);
        List<NotificationDTO> notifications = notificationService.getNotificationsByPhoneNumber(phoneNumber);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/message")
    public String getMessage() {
        log.info("getMessage -> calling");
        return message;
    }

    public String getMessageFallback(Throwable throwable) {
        log.info("getMessageFallback -> calling");
        return "FallBack message from fallback  method.";
    }
}