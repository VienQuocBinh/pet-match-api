package petmatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import petmatch.model.Subscription;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {
    Optional<List<Subscription>> findAllByUser_Id(String userId);

    Optional<Subscription> findByUser_IdAndNameAndStatus(String userId, String name, String status);
}
