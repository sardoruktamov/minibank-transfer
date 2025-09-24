package dasturlash.uz.card.entity;

import dasturlash.uz.card.enums.CardStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "card")
public class CardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "card_type")
    @Enumerated(EnumType.STRING)
    private CardStatus status;

    @Column(name = "amount")
    private Long amount = 0l;
    @Column(name = "owner_id")
    private String ownerId; // profileId
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
