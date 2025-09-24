package dasturlash.uz.transaction.repository;

import dasturlash.uz.transaction.entity.TransactionsEntity;
import dasturlash.uz.transaction.enums.TransactionStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.time.LocalDateTime;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionsEntity, String> {

    List<TransactionsEntity> findByFromCardId(String fromCardId);

    List<TransactionsEntity> findByToCardId(String toCardId);

    @Query("SELECT t FROM TransactionsEntity t WHERE t.fromCardId = :cardId OR t.toCardId = :cardId")
    List<TransactionsEntity> findByCardId(@Param("cardId") String cardId);

    List<TransactionsEntity> findByTransactionStatus(TransactionStatusEnum status);

    @Query("SELECT t FROM TransactionsEntity t WHERE t.fromCardId = :fromCardId AND t.toCardId = :toCardId")
    List<TransactionsEntity> findByFromCardIdAndToCardId(@Param("fromCardId") String fromCardId,
                                                         @Param("toCardId") String toCardId);

    @Query("SELECT t FROM TransactionsEntity t WHERE t.createdDate BETWEEN :startDate AND :endDate")
    List<TransactionsEntity> findByDateRange(@Param("startDate") LocalDateTime startDate,
                                             @Param("endDate") LocalDateTime endDate);

    @Query("SELECT t FROM TransactionsEntity t WHERE t.fromCardId = :cardId AND t.createdDate BETWEEN :startDate AND :endDate")
    List<TransactionsEntity> findByFromCardIdAndDateRange(@Param("cardId") String cardId,
                                                          @Param("startDate") LocalDateTime startDate,
                                                          @Param("endDate") LocalDateTime endDate);

    @Query("SELECT SUM(t.amount) FROM TransactionsEntity t WHERE t.fromCardId = :cardId AND t.transactionStatus = 'COMPLETED'")
    Long getTotalAmountByFromCardId(@Param("cardId") String cardId);

    @Query("SELECT SUM(t.amount) FROM TransactionsEntity t WHERE t.toCardId = :cardId AND t.transactionStatus = 'COMPLETED'")
    Long getTotalAmountByToCardId(@Param("cardId") String cardId);
}
