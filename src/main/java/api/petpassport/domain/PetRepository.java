package api.petpassport.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PetRepository extends JpaRepository<PetEntity, UUID> {

    @Query(
            """
                    select p from PetEntity p
                    left join fetch UserPetsEntity up on up.id.petId = p.id
                    where up.id.userId = :userId
                    """
    )
    Page<PetEntity> findByUserId(@Param(value = "userId") UUID userId, Pageable pageable);

    @Query(
            """
                    select p from PetEntity p
                           left join fetch UserPetsEntity up on up.id.petId = p.id
                           where up.id.userId = :userId and
                           up.id.petId = :petId
                           """
    )
    Optional<PetEntity> getByUserIdAndPetId(@Param(value = "userId") UUID userId,
                                    @Param(value = "petId") UUID petId);

}
