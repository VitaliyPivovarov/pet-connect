package api.petpassport.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IdentificationCreateUpdateDto {

    private String number;
    private Instant microChippedAt;
    private String location;
    private String tattooNumber;
    private Instant tattooingAt;
    private String distinctiveMark;
    private Instant reproductionData;
}
