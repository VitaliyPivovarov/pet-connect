package api.petpassport.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IdentificationRepository extends JpaRepository<IdentificatonEntity, UUID> {

    Optional<IdentificatonEntity> getByPetId(UUID petId);

}
