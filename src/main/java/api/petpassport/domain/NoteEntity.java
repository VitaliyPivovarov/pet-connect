package api.petpassport.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "note")
public class NoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "pet_id", nullable = false)
    private UUID petId;

    @Column(name = "value", nullable = false)
    private String value;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "update_at", nullable = false)
    private Instant updateAt;

    public static NoteEntity create(UUID petId, String value) {
        NoteEntity note = new NoteEntity();
        note.setPetId(petId);
        note.setValue(value);
        note.setCreatedAt(Instant.now());
        note.setUpdateAt(Instant.now());
        return note;
    }

}
