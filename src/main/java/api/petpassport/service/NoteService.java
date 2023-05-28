package api.petpassport.service;

import api.petpassport.controller.dto.NoteCreateUpdateDto;
import api.petpassport.domain.NoteEntity;
import api.petpassport.domain.NoteRepository;
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
public class NoteService {

    private final NoteRepository noteRepository;
    private final PetRepository petRepository;

    public Page<NoteEntity> findAllByPetId(UUID userId, UUID petId, final int page, final int size) {
        Pageable pageable = PageRequest.of(page, size);
        petRepository.getByUserIdAndPetId(userId, petId)
                .orElseThrow(() -> new NotFoundException("Pet not found!"));
        return noteRepository.findByPetId(petId, pageable);
    }

    @Transactional
    public NoteEntity create(UUID userId, UUID petId,
                             NoteCreateUpdateDto createDto) {
        petRepository.getByUserIdAndPetId(userId, petId)
                .orElseThrow(() -> new NotFoundException("Pet not found!"));

        NoteEntity note = NoteEntity.create(
                petId,
                createDto.getValue()
        );
        note = noteRepository.save(note);

        log.info("Note value:{} has created", createDto.getValue());
        return note;
    }

    @Transactional
    public void delete(UUID userId, UUID petId, UUID clinicalExaminationId) {
        PetEntity pet = petRepository.getByUserIdAndPetId(userId, petId)
                .orElseThrow(() -> new NotFoundException("Pet not found!"));

        NoteEntity note = noteRepository.findById(clinicalExaminationId)
                .orElseThrow(() -> new NotFoundException("Note not found!"));

        noteRepository.delete(note);
        log.info("Note value:{} has deleted", note.getValue());
    }

}
