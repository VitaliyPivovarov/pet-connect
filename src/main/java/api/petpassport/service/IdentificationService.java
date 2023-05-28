package api.petpassport.service;

import api.petpassport.controller.dto.IdentificationCreateUpdateDto;
import api.petpassport.domain.IdentificationRepository;
import api.petpassport.domain.IdentificatonEntity;
import api.petpassport.domain.PetEntity;
import api.petpassport.domain.PetRepository;
import api.petpassport.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class IdentificationService {

    private final IdentificationRepository identificationRepository;
    private final PetRepository petRepository;

    public IdentificatonEntity get(UUID userId, UUID petId) {
        petRepository.getByUserIdAndPetId(userId, petId)
                .orElseThrow(() -> new NotFoundException("Pet not found!"));

        IdentificatonEntity identificaton = identificationRepository.getByPetId(petId)
                .orElseThrow(() -> new NotFoundException("Identification not found!"));
        return identificaton;
    }

    @Transactional
    public IdentificatonEntity create(UUID userId, UUID petId,
                                      IdentificationCreateUpdateDto createDto) {
        petRepository.getByUserIdAndPetId(userId, petId)
                .orElseThrow(() -> new NotFoundException("Pet not found!"));

        IdentificatonEntity identificaton = IdentificatonEntity.create(
                petId,
                createDto.getNumber(),
                createDto.getMicroChippedAt(),
                createDto.getLocation(),
                createDto.getTattooNumber(),
                createDto.getTattooingAt(),
                createDto.getDistinctiveMark(),
                createDto.getReproductionData()
        );
        identificaton = identificationRepository.save(identificaton);

        log.info("Identification number:{} has created", createDto.getNumber());
        return identificaton;
    }

    @Transactional
    public IdentificatonEntity update(UUID userId, UUID petId, UUID identificationId,
                                      IdentificationCreateUpdateDto updateDto) {
        PetEntity pet = petRepository.getByUserIdAndPetId(userId, petId)
                .orElseThrow(() -> new NotFoundException("Pet not found!"));

        IdentificatonEntity identification = identificationRepository.findById(identificationId)
                .orElseThrow(() -> new NotFoundException("Identificaton not found!"));

        identification.update(
                petId,
                updateDto.getNumber(),
                updateDto.getMicroChippedAt(),
                updateDto.getLocation(),
                updateDto.getTattooNumber(),
                updateDto.getTattooingAt(),
                updateDto.getDistinctiveMark(),
                updateDto.getReproductionData()
        );
        identification = identificationRepository.save(identification);

        log.info("Identification name:{} has updated", updateDto.getNumber());
        return identification;
    }

    @Transactional
    public void delete(UUID userId, UUID petId, UUID identificationId) {
        PetEntity pet = petRepository.getByUserIdAndPetId(userId, petId)
                .orElseThrow(() -> new NotFoundException("Pet not found!"));

        IdentificatonEntity identification = identificationRepository.findById(identificationId)
                .orElseThrow(() -> new NotFoundException("Identificaton not found!"));

        identificationRepository.delete(identification);
        log.info("Identification name:{} has deleted", pet.getName());
    }

}
