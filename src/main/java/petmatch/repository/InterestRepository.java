package petmatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import petmatch.model.Interests;
import petmatch.model.Profile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InterestRepository extends JpaRepository<Interests, UUID> {

    Optional<List<Interests>> findAllByProfile(Profile profile);

}
