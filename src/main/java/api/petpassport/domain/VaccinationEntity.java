package api.petpassport.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "vaccinations")
public class VaccinationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "pet_id", nullable = false)
    private UUID petId;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "manufacturer", nullable = false)
    private String manufacturer;

    @Column(name = "batch_number", nullable = false)
    private String batchNumber;

    @Column(name = "manufactured_at", nullable = false)
    private Instant manufacturedAt;

    @Column(name = "best_before_at", nullable = false)
    private Instant bestBeforeAt;

    @Column(name = "vaccinationAt", nullable = false)
    private Instant vaccinationAt;

    @Column(name = "vaccination_until_before_at", nullable = false)
    private Instant vaccinationUntilBeforeAt;

    @Column(name = "authorised_veterinarian", nullable = false)
    private String authorisedVeterinarian;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "update_at", nullable = false)
    private Instant updateAt;

    public static VaccinationEntity create(UUID petId, String type,
                                           String name, String manufacturer,
                                           String batchNumber, Instant manufacturedAt,
                                           Instant bestBeforeAt, Instant vaccinationAt,
                                           Instant vaccinationUntilBeforeAt,
                                           String authorisedVeterinarian) {
        VaccinationEntity vaccination = new VaccinationEntity();
        vaccination.setPetId(petId);
        vaccination.setType(type);
        vaccination.setName(name);
        vaccination.setManufacturer(manufacturer);
        vaccination.setBatchNumber(batchNumber);
        vaccination.setManufacturedAt(manufacturedAt);
        vaccination.setBestBeforeAt(bestBeforeAt);
        vaccination.setVaccinationAt(vaccinationAt);
        vaccination.setVaccinationUntilBeforeAt(vaccinationUntilBeforeAt);
        vaccination.setAuthorisedVeterinarian(authorisedVeterinarian);
        vaccination.setCreatedAt(Instant.now());
        vaccination.setUpdateAt(Instant.now());
        return vaccination;
    }

}
