package api.petpassport.service;

import api.petpassport.config.security.dto.JwtDto;
import api.petpassport.controller.dto.SignInDto;
import api.petpassport.domain.user.UserEntity;
import api.petpassport.domain.user.UserRepository;
import api.petpassport.exception.ConflictException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value(value = "${jwt.secret}")
    private String jwtSecret;

    @Value(value = "${jwt.valid}")
    private Long jwtValid;

    @Transactional
    public JwtDto signIn(@Valid @RequestBody SignInDto signInDto) {
        UserEntity user = userRepository.findByEmail(signInDto.getEmail())
                .orElseThrow(() -> new ConflictException("Email or password are not valid!"));

        if (!passwordEncoder.matches(signInDto.getPassword(), user.getPassword())) {
            throw new ConflictException("Wrong email or password!");
        }

        return JwtService.buildToken(jwtSecret, jwtValid, user.getId());
    }

}