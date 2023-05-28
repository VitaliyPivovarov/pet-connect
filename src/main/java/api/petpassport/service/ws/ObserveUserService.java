package api.petpassport.service.ws;

import api.petpassport.config.security.dto.PrincipalDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class ObserveUserService {

    //for disconnected users, key = ws sessionId
    private final Map<String, PrincipalDto> sessionPrincipalHub = new ConcurrentHashMap<>();
    //key = userId, value = random uuid for secure ws username
    private final Map<UUID, String> activeWsUserUUIDs = new ConcurrentHashMap<>();

    public void connect(String sessionId, Principal principal) {
        PrincipalDto principalDto = (PrincipalDto) principal;
        sessionPrincipalHub.put(sessionId, principalDto);
        activeWsUserUUIDs.put(principalDto.getUserId(), principalDto.getWsUserName());

        log.info("User id: {} has connected", principalDto.getUserId());
    }

    public void disconnect(String sessionId) {
        PrincipalDto principalDto = sessionPrincipalHub.remove(sessionId);
        if (principalDto != null) {
            activeWsUserUUIDs.remove(principalDto.getUserId());
            log.info("User id: {} has disconnected", principalDto.getUserId());
        }
    }

    public Optional<String> findWsUserNameByUserId(UUID userId) {
        return Optional.ofNullable(activeWsUserUUIDs.get(userId));
    }

}
