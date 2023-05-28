package api.petpassport.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "user_pets")
public class UserPetsEntity {

    @EmbeddedId
    private UserPetsDomainKey id;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "update_at", nullable = false)
    private Instant updateAt;

    @Embeddable
    @Getter
    @Setter
    @EqualsAndHashCode
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserPetsDomainKey implements Serializable {

        @Column(name = "user_id", nullable = false)
        private UUID userId;

        @Column(name = "pet_id", nullable = false)
        private UUID petId;

    }

    public static UserPetsEntity create(UUID userId, UUID petId) {
        UserPetsEntity t = new UserPetsEntity();
        UserPetsDomainKey key = new UserPetsDomainKey();
        key.setUserId(userId);
        key.setPetId(petId);
        t.setId(key);
        t.setCreatedAt(Instant.now());
        t.setUpdateAt(Instant.now());
        return t;
    }
}
