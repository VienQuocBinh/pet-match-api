package petmatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import petmatch.model.Reaction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, UUID> {

    @Query("SELECT r FROM Reaction r WHERE r.createdBy.id = :createdById")
    Optional<List<Reaction>> findAllByCreatedBy(@Param("createdById")UUID id);

    @Query("SELECT r FROM Reaction r WHERE r.profile.id = :profileId")
    Optional<List<Reaction>> findAllByProfileId(@Param("profileId") UUID profileId);
}
