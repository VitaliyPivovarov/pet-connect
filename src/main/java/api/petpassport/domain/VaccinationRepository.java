package api.petpassport.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VaccinationRepository extends JpaRepository<VaccinationEntity, UUID> {

    Optional<VaccinationEntity> getByPetId(UUID petId);

    @Query(
            """
                    select v from VaccinationEntity v
                    where v.petId = :petId
                    """
    )
    Page<VaccinationEntity> findByPetId(@Param(value = "petId") UUID petId, Pageable pageable);

    List<VaccinationEntity> findAllByBestBeforeAtLessThan(Instant time);
}
