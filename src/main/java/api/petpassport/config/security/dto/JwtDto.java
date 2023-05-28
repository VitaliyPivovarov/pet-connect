package api.petpassport.config.security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record JwtDto(@JsonProperty("accessToken")
                     String accessToken) {

}
