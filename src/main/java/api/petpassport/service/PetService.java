package api.petpassport.service;

import api.petpassport.controller.dto.PetCreateUpdateDto;
import api.petpassport.domain.PetEntity;
import api.petpassport.domain.PetRepository;
import api.petpassport.domain.UserPetsEntity;
import api.petpassport.domain.UserPetsRepository;
import api.petpassport.exception.ConflictException;
import api.petpassport.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    //todo fix
    private final UserPetsRepository userPetsRepository;

    @Transactional
    public PetEntity create(UUID userId, PetCreateUpdateDto createDto) {
        PetEntity pet = PetEntity.create(
                createDto.getAvatarUri(),
                createDto.getName(),
                createDto.getBreed(),
                createDto.getSex(),
                createDto.getDateOfBirth(),
                createDto.getCoat(),
                createDto.getCoatVariety(),
                createDto.getType()
        );
        pet = petRepository.save(pet);

        UserPetsEntity userPets = UserPetsEntity.create(userId, pet.getId());
        userPetsRepository.save(userPets);

        log.info("Pet name:{} has created", createDto.getName());
        return pet;
    }

    public PetEntity getById(UUID petId) {
        return petRepository.findById(petId)
                .orElseThrow(() -> new ConflictException("Pet not found!"));
    }

    public Page<PetEntity> findByUserId(UUID userId, final int page, final int size) {
        Pageable pageable = PageRequest.of(page, size);
        return petRepository.findByUserId(userId, pageable);
    }

    @Transactional
    public PetEntity update(UUID userId, UUID petId, PetCreateUpdateDto updateDto) {
        PetEntity pet = petRepository.getByUserIdAndPetId(userId, petId)
                .orElseThrow(() -> new NotFoundException("Pet not found!"));

        pet.update(
                updateDto.getAvatarUri(),
                updateDto.getName(),
                updateDto.getBreed(),
                updateDto.getSex(),
                updateDto.getDateOfBirth(),
                updateDto.getCoat(),
                updateDto.getCoatVariety(),
                updateDto.getType()
        );
        pet = petRepository.save(pet);

        log.info("Pet name:{} has updated", updateDto.getName());
        return pet;
    }

    @Transactional
    public void delete(UUID userId, UUID petId) {
        PetEntity pet = petRepository.getByUserIdAndPetId(userId, petId)
                .orElseThrow(() -> new NotFoundException("Pet not found!"));

        petRepository.delete(pet);
        log.info("Pet name:{} has deleted", pet.getName());

    }

}
