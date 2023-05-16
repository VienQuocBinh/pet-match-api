package petmatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import petmatch.model.Gallery;
import petmatch.model.Profile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GalleryRepository extends JpaRepository<Gallery, UUID> {
    Optional<List<Gallery>> findAllByProfile(Profile profile);
}
