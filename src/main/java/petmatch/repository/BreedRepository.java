package petmatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import petmatch.model.Breed;

import java.util.UUID;

public interface BreedRepository extends JpaRepository<Breed, UUID> {

}
