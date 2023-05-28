package api.petpassport.service.user;

import api.petpassport.adapter.NotificationOutput;
import api.petpassport.adapter.dto.WsNotificationDto;
import api.petpassport.domain.service.UserServiceRequestsEntity;
import api.petpassport.domain.service.UserServiceRequestsRepository;
import api.petpassport.domain.service.UserServicesEntity;
import api.petpassport.domain.service.UserServicesRepository;
import api.petpassport.domain.user.UserEntity;
import api.petpassport.domain.user.UserRepository;
import api.petpassport.exception.ConflictException;
import api.petpassport.service.EmailNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceRequestsService {

    private final UserServiceRequestsRepository userServiceRequestsRepository;
    private final UserServicesRepository userServicesRepository;
    private final UserRepository userRepository;
    private final EmailNotificationService emailNotificationService;
    private final NotificationOutput notificationOutput;

    public Page<UserServiceRequestsEntity> findForCustomer(UUID userId, final int page, final int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userServiceRequestsRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
    }

    public Page<UserServiceRequestsEntity> findForExecutor(UUID userId, final int page, final int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userServiceRequestsRepository.findForExecutorUserIdOrderByCreatedAtDesc(userId, pageable);
    }

    @Transactional
    public UserServiceRequestsEntity create(UUID userId, UUID serviceId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ConflictException("User not found!"));

        UserServicesEntity userServicesEntity = userServicesRepository.findById(serviceId).orElseThrow(() -> new ConflictException("Service not found!"));
        UserServiceRequestsEntity userServiceRequestsEntity = UserServiceRequestsEntity
                .create(userServicesEntity, user);
        userServiceRequestsEntity = userServiceRequestsRepository.save(userServiceRequestsEntity);
        emailNotificationService.sendEmail(userServicesEntity.getUser().getEmail(),
                "У вас новая заявка на услугу",
                "Уважаемый " + userServiceRequestsEntity.getUser().getName() +
                        ", у Вас новая заявка от " + user.getName());
        notificationOutput.sendMessage(userServicesEntity.getUser().getId(),
                new WsNotificationDto("Новая заявка",
                        "Новая заявка от " + user.getName())
        );
        return userServiceRequestsEntity;
    }

}
