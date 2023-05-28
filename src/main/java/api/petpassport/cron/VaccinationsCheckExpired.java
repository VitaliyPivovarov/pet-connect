package api.petpassport.cron;

import api.petpassport.service.VaccinationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class VaccinationsCheckExpired {

    private final VaccinationService vaccinationService;

    @Scheduled(cron = "0 0 9-21 * * *")
    public void run() {
        log.info("run check vaccination expired");
        vaccinationService.checkAndSend();
    }
}
