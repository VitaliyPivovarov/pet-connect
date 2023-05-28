package api.petpassport.service.enrich;

import api.petpassport.service.ws.ObserveUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserEnrichService {

    private final ObserveUserService observeUserService;

    public void setOnlineStatus(OnlineModel userShortDto) {
        observeUserService.findWsUserNameByUserId(userShortDto.getId())
                .ifPresent(user -> userShortDto.setOnline(true));
    }
}
