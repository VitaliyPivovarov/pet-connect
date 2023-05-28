package api.petpassport.domain.service;

import api.petpassport.enums.PetTypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserServicesRepository extends JpaRepository<UserServicesEntity, UUID> {

    Optional<UserServicesEntity> findByIdAndUserId(UUID id, UUID userId);

    Page<UserServicesEntity> findByUserId(UUID userId, Pageable pageable);

    Page<UserServicesEntity> findByServiceTypeStartingWithAndPetType(@Param(value = "serviceType") String serviceType,
                                                             @Param(value = "petTypeEnum") PetTypeEnum petTypeEnum,
                                                             Pageable pageable);

}
