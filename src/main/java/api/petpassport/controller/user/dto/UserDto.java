package api.petpassport.controller.user.dto;

import api.petpassport.service.enrich.OnlineModel;
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
public class UserDto implements OnlineModel {

    private UUID id;
    private boolean isOnline;
    private String avatarUri;
    private String communicationType;
    private String email;
    private String name;
    private String surname;
    private String address;
    private String postCode;
    private String country;
    private String city;
    private Instant createdAt;
    private Instant updateAt;

}
