package api.petpassport.config.security.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.security.Principal;
import java.util.Objects;
import java.util.UUID;

@Getter
public class PrincipalDto implements Principal {

    private final UUID userId;
    private final String wsUserName;

    @JsonCreator
    public PrincipalDto(final @JsonProperty("userId") UUID userId,
                        final @JsonProperty("wsUserName") String wsUserName) {
        this.userId = userId;
        this.wsUserName = wsUserName;
    }

    @Override
    public String toString() {
        return "?";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrincipalDto that = (PrincipalDto) o;
        return Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public String getName() {
        return wsUserName;
    }
}
