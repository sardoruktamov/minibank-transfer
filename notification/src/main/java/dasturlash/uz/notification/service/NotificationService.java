package dasturlash.uz.notification.service;

import dasturlash.uz.notification.dto.NotificationCreateDTO;
import dasturlash.uz.notification.dto.NotificationDTO;
import dasturlash.uz.notification.entity.NotificationEntity;
import dasturlash.uz.notification.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public NotificationDTO createNotification(NotificationCreateDTO createDTO) {
        NotificationEntity notification = new NotificationEntity();
        notification.setMessage(createDTO.getMessage().trim());
        notification.setPhoneNumber(createDTO.getPhoneNumber().trim());
        notification.setCreatedDate(LocalDateTime.now());

        NotificationEntity savedNotification = notificationRepository.save(notification);
        return convertToDTO(savedNotification);
    }

    public List<NotificationDTO> getNotificationsByPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("To account is required");
        }

        return notificationRepository.findByPhoneNumberOrderByCreatedDateDesc(phoneNumber.trim())
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private NotificationDTO convertToDTO(NotificationEntity notification) {
        NotificationDTO dto = new NotificationDTO();
        dto.setId(notification.getId());
        dto.setMessage(notification.getMessage());
        dto.setPhoneNumber(notification.getPhoneNumber());
        dto.setCreatedDate(notification.getCreatedDate());
        return dto;
    }
}
