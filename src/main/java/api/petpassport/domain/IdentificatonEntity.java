package api.petpassport.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "identifications")
public class IdentificatonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "pet_id", nullable = false)
    private UUID petId;

    @Column(name = "number", nullable = false)
    private String number;

    @Column(name = "micro_chipped_at", nullable = false)
    private Instant microChippedAt;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "tattoo_number", nullable = false)
    private String tattooNumber;

    @Column(name = "tattooing_at", nullable = false)
    private Instant tattooingAt;

    @Column(name = "distinctive_mark", nullable = false)
    private String distinctiveMark;

    @Column(name = "reproduction_data", nullable = false)
    private Instant reproductionData;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "update_at", nullable = false)
    private Instant updateAt;

    public static IdentificatonEntity create(UUID petId, String number,
                                             Instant microChippedAt, String location,
                                             String tattooNumber, Instant tattooingAt,
                                             String distinctiveMark, Instant reproductionData) {
        IdentificatonEntity identificaton = new IdentificatonEntity();
        identificaton.setPetId(petId);
        identificaton.setNumber(number);
        identificaton.setMicroChippedAt(microChippedAt);
        identificaton.setLocation(location);
        identificaton.setTattooNumber(tattooNumber);
        identificaton.setTattooingAt(tattooingAt);
        identificaton.setDistinctiveMark(distinctiveMark);
        identificaton.setReproductionData(reproductionData);
        identificaton.setCreatedAt(Instant.now());
        identificaton.setUpdateAt(Instant.now());
        return identificaton;
    }

    public void update(UUID petId, String number,
                       Instant microChippedAt, String location,
                       String tattooNumber, Instant tattooingAt,
                       String distinctiveMark, Instant reproductionData) {
        this.setPetId(petId);
        this.setNumber(number);
        this.setMicroChippedAt(microChippedAt);
        this.setLocation(location);
        this.setTattooNumber(tattooNumber);
        this.setTattooingAt(tattooingAt);
        this.setDistinctiveMark(distinctiveMark);
        this.setReproductionData(reproductionData);
        this.setCreatedAt(Instant.now());
        this.setUpdateAt(Instant.now());
        this.setUpdateAt(Instant.now());
    }
}
