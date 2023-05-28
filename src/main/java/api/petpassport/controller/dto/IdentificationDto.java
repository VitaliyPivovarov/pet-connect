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
public class IdentificationDto {

    private UUID id;
    private String number;
    private Instant microChippedAt;
    private String location;
    private String tattooNumber;
    private Instant tattooingAt;
    private String distinctiveMark;
    private Instant reproductionData;
    private Instant createdAt;
    private Instant updateAt;
}
