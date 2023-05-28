package api.petpassport.service;

import api.petpassport.controller.dto.ClinicalExaminationCreateUpdateDto;
import api.petpassport.domain.ClinicalExaminationEntity;
import api.petpassport.domain.ClinicalExaminationRepository;
import api.petpassport.domain.PetEntity;
import api.petpassport.domain.PetRepository;
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
public class ClinicalExaminationService {

    private final ClinicalExaminationRepository clinicalExaminationRepository;
    private final PetRepository petRepository;

    public Page<ClinicalExaminationEntity> findAllByPetId(UUID userId, UUID petId, final int page, final int size) {
        Pageable pageable = PageRequest.of(page, size);
        petRepository.getByUserIdAndPetId(userId, petId)
                .orElseThrow(() -> new NotFoundException("Pet not found!"));
        return clinicalExaminationRepository.findByPetId(petId, pageable);
    }

    @Transactional
    public ClinicalExaminationEntity create(UUID userId, UUID petId,
                                            ClinicalExaminationCreateUpdateDto createDto) {
        petRepository.getByUserIdAndPetId(userId, petId)
                .orElseThrow(() -> new NotFoundException("Pet not found!"));

        ClinicalExaminationEntity clinicalExamination = ClinicalExaminationEntity.create(
                petId,
                createDto.getAssessmentReport(),
                createDto.getAuthorisedVeterinarian()
        );
        clinicalExamination = clinicalExaminationRepository.save(clinicalExamination);

        log.info("ClinicalExamination report:{} has created", createDto.getAssessmentReport());
        return clinicalExamination;
    }

    @Transactional
    public void delete(UUID userId, UUID petId, UUID clinicalExaminationId) {
        PetEntity pet = petRepository.getByUserIdAndPetId(userId, petId)
                .orElseThrow(() -> new NotFoundException("Pet not found!"));

        ClinicalExaminationEntity clinicalExamination = clinicalExaminationRepository.findById(clinicalExaminationId)
                .orElseThrow(() -> new NotFoundException("ClinicalExamination not found!"));

        clinicalExaminationRepository.delete(clinicalExamination);
        log.info("ClinicalExamination report:{} has deleted", clinicalExamination.getAssessmentReport());
    }

}
