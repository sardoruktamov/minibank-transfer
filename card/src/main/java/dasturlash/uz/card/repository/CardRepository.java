package dasturlash.uz.card.repository;

import dasturlash.uz.card.entity.CardEntity;
import dasturlash.uz.card.enums.CardStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<CardEntity, String> {

    Optional<CardEntity> findByCardNumber(String cardNumber);

    List<CardEntity> findByOwnerId(String ownerId);

    List<CardEntity> findByPhoneNumber(String phoneNumber);

    @Query("SELECT c FROM CardEntity c WHERE c.status = :status")
    List<CardEntity> findByStatus(@Param("status") CardStatus status);

    boolean existsByCardNumber(String cardNumber);

    boolean existsByPhoneNumber(String phoneNumber);
}