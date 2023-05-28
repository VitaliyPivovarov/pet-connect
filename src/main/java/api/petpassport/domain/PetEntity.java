package api.petpassport.domain;

import api.petpassport.domain.user.UserEntity;
import api.petpassport.enums.PetTypeEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "pets")
public class PetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "avatar_uri")
    private String avatarUri;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private PetTypeEnum type;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "breed", nullable = false)
    private String breed;

    @Column(name = "sex", nullable = false)
    private String sex;

    @Column(name = "date_of_birth", nullable = false)
    private Instant dateOfBirth;

    @Column(name = "coat")
    private String coat;

    @Column(name = "coat_variety")
    private String coatVariety;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "update_at", nullable = false)
    private Instant updateAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_pets",
            joinColumns = @JoinColumn(name = "pet_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<UserEntity> users = new ArrayList<>();

    public static PetEntity create(String avatarUri, String name, String breed,
                                   String sex, Instant dateOfBirth,
                                   String coat, String coatVariety,
                                   PetTypeEnum type) {
        PetEntity pet = new PetEntity();
        pet.setAvatarUri(avatarUri);
        pet.setName(name);
        pet.setBreed(breed);
        pet.setSex(sex);
        pet.setDateOfBirth(dateOfBirth);
        pet.setCoat(coat);
        pet.setCoatVariety(coatVariety);
        pet.setType(type);
        pet.setCreatedAt(Instant.now());
        pet.setUpdateAt(Instant.now());
        return pet;
    }

    public void update(String avatarUri, String name, String breed,
                       String sex, Instant dateOfBirth,
                       String coat, String coatVariety,
                       PetTypeEnum type) {
        this.setAvatarUri(avatarUri);
        this.setName(name);
        this.setBreed(breed);
        this.setSex(sex);
        this.setDateOfBirth(dateOfBirth);
        this.setCoat(coat);
        this.setType(type);
        this.setCoatVariety(coatVariety);
        this.setUpdateAt(Instant.now());
    }
}
