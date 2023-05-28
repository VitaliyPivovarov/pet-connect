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
public class UserCustomerDto implements OnlineModel {

    private UUID id;
    private boolean isOnline;
    private String avatarUri;
    private String communicationType;
    private Instant createdAt;
    private Instant updateAt;

}
