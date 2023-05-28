package api.petpassport.service.user;

import api.petpassport.controller.user.dto.UserCreateDto;
import api.petpassport.controller.user.dto.UserUpdateDto;
import api.petpassport.domain.user.UserEntity;
import api.petpassport.domain.user.UserRepository;
import api.petpassport.exception.ConflictException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserEntity create(UserCreateDto createDto) {
        if (userRepository.existsByEmail(createDto.getEmail())) {
            log.info("Email: {} already exists!", createDto.getEmail());
            throw new ConflictException("Email already exists!");
        }

        UserEntity user = UserEntity.create(createDto.getAvatarUri(),
                createDto.getEmail(),
                passwordEncoder.encode(createDto.getPassword()),
                createDto.getName(), createDto.getSurname(),
                createDto.getAddress(), createDto.getPostCode(),
                createDto.getCountry(), createDto.getCity(), createDto.getCommunicationType());
        user = userRepository.save(user);
        log.info("Email: {} has created", user.getEmail());
        return user;
    }

    public boolean exist(String email) {
        return userRepository.existsByEmail(email);
    }

    public UserEntity getById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ConflictException("User not found!"));
    }

    @Transactional
    public UserEntity update(UUID userId, UserUpdateDto updateDto) {
        UserEntity user = getById(userId);
        user.update(updateDto.getAvatarUri(),
                updateDto.getName(), updateDto.getSurname(),
                updateDto.getAddress(), updateDto.getPostCode(),
                updateDto.getCountry(), updateDto.getCity(), updateDto.getCommunicationType());
        user = userRepository.save(user);
        log.info("Email: {} jas updated", user.getEmail());
        return user;
    }

}
