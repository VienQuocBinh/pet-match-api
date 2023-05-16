package petmatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import petmatch.model.Breed;

import java.util.UUID;

@Repository
public interface BreedRepository extends JpaRepository<Breed, UUID> {
}
