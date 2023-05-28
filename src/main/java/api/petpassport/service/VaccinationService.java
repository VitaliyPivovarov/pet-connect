package api.petpassport.service;

import api.petpassport.controller.dto.VaccinationCreateUpdateDto;
import api.petpassport.domain.*;
import api.petpassport.domain.user.UserRepository;
import api.petpassport.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class VaccinationService {

    private final VaccinationRepository vaccinationRepository;
    private final PetRepository petRepository;
    private final UserPetsRepository userPetsRepository;
    private final UserRepository userRepository;
    private final EmailNotificationService emailNotificationService;

    public Page<VaccinationEntity> findAllByPetId(UUID userId, UUID petId, final int page, final int size) {
        Pageable pageable = PageRequest.of(page, size);
        petRepository.getByUserIdAndPetId(userId, petId)
                .orElseThrow(() -> new NotFoundException("Pet not found!"));
        return vaccinationRepository.findByPetId(petId, pageable);
    }

    @Transactional
    public VaccinationEntity create(UUID userId, UUID petId,
                                    VaccinationCreateUpdateDto createDto) {
        petRepository.getByUserIdAndPetId(userId, petId)
                .orElseThrow(() -> new NotFoundException("Pet not found!"));

        VaccinationEntity vaccination = VaccinationEntity.create(
                petId,
                createDto.getType(),
                createDto.getName(),
                createDto.getManufacturer(),
                createDto.getBatchNumber(),
                createDto.getManufacturedAt(),
                createDto.getBestBeforeAt(),
                createDto.getVaccinationAt(),
                createDto.getVaccinationUntilBeforeAt(),
                createDto.getAuthorisedVeterinarian()
        );
        vaccination = vaccinationRepository.save(vaccination);

        log.info("Vaccination name:{} has created", createDto.getName());
        return vaccination;
    }

    @Transactional
    public void delete(UUID userId, UUID petId, UUID vaccinationId) {
        PetEntity pet = petRepository.getByUserIdAndPetId(userId, petId)
                .orElseThrow(() -> new NotFoundException("Pet not found!"));

        VaccinationEntity vaccination = vaccinationRepository.findById(vaccinationId)
                .orElseThrow(() -> new NotFoundException("Vaccination not found!"));

        vaccinationRepository.delete(vaccination);
        log.info("Vaccination name:{} has deleted", vaccination.getName());
    }

    public void checkAndSend() {
        vaccinationRepository.findAllByBestBeforeAtLessThan(Instant.now())
                .forEach(vaccination -> {
                    petRepository.findById(vaccination.getPetId())
                            .ifPresent(pet -> {
                                userPetsRepository.findAllByIdPetId(pet.getId())
                                        .forEach(userPet -> {
                                            userRepository.findById(userPet.getId().getUserId())
                                                    .ifPresent(user -> {
                                                        new Thread(() -> {
                                                            emailNotificationService.sendEmail(user.getEmail(),
                                                                    "Обновление вакцинации",
                                                                    "Уважаемый " + user.getName() + ", " +
                                                                            "у Вашего питомца " + pet.getName() +
                                                                            " просрочилась вакцина " + vaccination.getName() +
                                                                            " от " + vaccination.getBestBeforeAt());
                                                        }).start();

                                                    });
                                        });
                            });
                });
    }

}
