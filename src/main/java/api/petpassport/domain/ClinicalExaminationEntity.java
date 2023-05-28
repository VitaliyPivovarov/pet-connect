package api.petpassport.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "clinical_examinations")
public class ClinicalExaminationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "pet_id", nullable = false)
    private UUID petId;

    @Column(name = "assessment_report", nullable = false)
    private String assessmentReport;

    @Column(name = "authorised_veterinarian", nullable = false)
    private String authorisedVeterinarian;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "update_at", nullable = false)
    private Instant updateAt;

    public static ClinicalExaminationEntity create(UUID petId, String assessmentReport,
                                                   String authorisedVeterinarian) {
        ClinicalExaminationEntity clinicalExamination = new ClinicalExaminationEntity();
        clinicalExamination.setPetId(petId);
        clinicalExamination.setAssessmentReport(assessmentReport);
        clinicalExamination.setAuthorisedVeterinarian(authorisedVeterinarian);
        clinicalExamination.setCreatedAt(Instant.now());
        clinicalExamination.setUpdateAt(Instant.now());
        return clinicalExamination;
    }

}
