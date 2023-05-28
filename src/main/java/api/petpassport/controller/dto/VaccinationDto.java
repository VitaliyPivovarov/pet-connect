package api.petpassport.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VaccinationDto {

    private UUID id;
    private UUID petId;
    private String type;
    private String name;
    private String manufacturer;
    private String batchNumber;
    private Instant manufacturedAt;
    private Instant bestBeforeAt;
    private Instant vaccinationAt;
    private Instant vaccinationUntilBeforeAt;
    private String authorisedVeterinarian;
    private Instant createdAt;
    private Instant updateAt;
}
