package api.petpassport.controller.service.dto;

import api.petpassport.enums.PetTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserServiceCreateUpdateDto {

    private PetTypeEnum petType;
    private String serviceType;
    private String message;
    private boolean departure;
    private Double priceFixed;
    private Double priceMin;
    private Double priceMax;
}
