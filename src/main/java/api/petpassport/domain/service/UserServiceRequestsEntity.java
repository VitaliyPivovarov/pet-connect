package api.petpassport.domain.service;

import api.petpassport.domain.user.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "user_service_requests")
public class UserServiceRequestsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_service_id", nullable = false)
    private UserServicesEntity userService;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "update_at", nullable = false)
    private Instant updateAt;

    public static UserServiceRequestsEntity create(UserServicesEntity userService,
                                                   UserEntity user) {
        UserServiceRequestsEntity userServiceRequestsEntity = new UserServiceRequestsEntity();
        userServiceRequestsEntity.setUserService(userService);
        userServiceRequestsEntity.setUser(user);
        userServiceRequestsEntity.setCreatedAt(Instant.now());
        userServiceRequestsEntity.setUpdateAt(Instant.now());
        return userServiceRequestsEntity;
    }

}
