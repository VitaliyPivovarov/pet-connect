package api.petpassport.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NoteRepository extends JpaRepository<NoteEntity, UUID> {

    @Query(
            """
                    select n from NoteEntity n
                    where n.petId = :petId
                    """
    )
    Page<NoteEntity> findByPetId(@Param(value = "petId") UUID petId, Pageable pageable);

}
