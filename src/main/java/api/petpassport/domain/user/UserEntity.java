package api.petpassport.domain.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "users")
@ToString(
        exclude = "password"
)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "avatar_uri")
    private String avatarUri;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "address")
    private String address;

    @Column(name = "post_code")
    private String postCode;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "communication_type")
    private String communicationType;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "update_at", nullable = false)
    private Instant updateAt;

    public static UserEntity create(String avatarUri, String email, String password,
                                    String name, String surname,
                                    String address, String postCode,
                                    String country, String city,
                                    String communicationType) {
        UserEntity user = new UserEntity();
        user.setAvatarUri(avatarUri);
        user.setEmail(email);
        user.setPassword(password);
        user.setName(name);
        user.setSurname(surname);
        user.setAddress(address);
        user.setPostCode(postCode);
        user.setCountry(country);
        user.setCity(city);
        user.setCommunicationType(communicationType);
        user.setCreatedAt(Instant.now());
        user.setUpdateAt(Instant.now());
        return user;
    }

    public void update(String avatarUri, String name, String surname,
                       String address, String postCode,
                       String country, String city,
                       String communicationType) {
        this.setAvatarUri(avatarUri);
        this.setName(name);
        this.setSurname(surname);
        this.setAddress(address);
        this.setPostCode(postCode);
        this.setCountry(country);
        this.setCity(city);
        this.setCommunicationType(communicationType);
        this.setCreatedAt(Instant.now());
        this.setUpdateAt(Instant.now());
    }
}
