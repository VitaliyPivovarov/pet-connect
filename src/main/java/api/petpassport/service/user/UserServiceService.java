package api.petpassport.service.user;

import api.petpassport.controller.service.dto.UserServiceCreateUpdateDto;
import api.petpassport.domain.service.UserServicesEntity;
import api.petpassport.domain.service.UserServicesRepository;
import api.petpassport.domain.user.UserEntity;
import api.petpassport.domain.user.UserRepository;
import api.petpassport.enums.PetTypeEnum;
import api.petpassport.exception.ConflictException;
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
public class UserServiceService {

    private final UserServicesRepository userServicesRepository;
    private final UserRepository userRepository;

    public Page<UserServicesEntity> findByUserId(UUID userId, final int page, final int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userServicesRepository.findByUserId(userId, pageable);
    }

    public Page<UserServicesEntity> search(PetTypeEnum petTypeEnum, String serviceType,
                                           final int page, final int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userServicesRepository.findByServiceTypeLikeAndPetType(serviceType, petTypeEnum, pageable);
    }

    @Transactional
    public UserServicesEntity create(UUID userId, UserServiceCreateUpdateDto createDto) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ConflictException("User not found!"));
        UserServicesEntity userServicesEntity = UserServicesEntity.create(
                user,
                createDto.getServiceType(),
                createDto.getPetType(),
                createDto.getMessage(),
                createDto.isDeparture(),
                createDto.getPriceFixed(),
                createDto.getPriceMin(),
                createDto.getPriceMax());

        userServicesEntity = userServicesRepository.save(userServicesEntity);
        log.info("UserServices type: {} has created!", createDto.getServiceType());
        return userServicesEntity;
    }

    @Transactional
    public UserServicesEntity update(UUID userId, UUID serviceId, UserServiceCreateUpdateDto updateDto) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ConflictException("User not found!"));
        UserServicesEntity userServicesEntity = userServicesRepository.findByIdAndUserId(serviceId, userId)
                .orElseThrow(() -> new ConflictException("Service not found!"));
        userServicesEntity.update(updateDto.getServiceType(), updateDto.getPetType(),
                updateDto.getMessage(), updateDto.isDeparture(),
                updateDto.getPriceFixed(),
                updateDto.getPriceMin(),
                updateDto.getPriceMax());
        return userServicesEntity;
    }

    @Transactional
    public void delete(UUID userId, UUID serviceId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ConflictException("User not found!"));
        UserServicesEntity userServicesEntity = userServicesRepository.findByIdAndUserId(serviceId, userId)
                .orElseThrow(() -> new ConflictException("Service not found!"));
        userServicesRepository.delete(userServicesEntity);
    }

}
