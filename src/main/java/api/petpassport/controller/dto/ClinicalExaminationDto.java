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
public class ClinicalExaminationDto {

    private UUID id;
    private UUID petId;
    private String assessmentReport;
    private String authorisedVeterinarian;
    private Instant createdAt;
    private Instant updateAt;
}
