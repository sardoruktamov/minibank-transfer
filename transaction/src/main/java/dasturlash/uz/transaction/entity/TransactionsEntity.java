package dasturlash.uz.transaction.entity;

import dasturlash.uz.transaction.enums.TransactionStatusEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "transactions")
public class TransactionsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "from_card_id", nullable = false)
    private String fromCardId;

    @Column(name = "to_card_id", nullable = false)
    private String toCardId;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Column(name = "transaction_status")
    @Enumerated(EnumType.STRING)
    private TransactionStatusEnum transactionStatus;

    @CreationTimestamp
    @Column(name = "created_date")
    private LocalDateTime createdDate;
}