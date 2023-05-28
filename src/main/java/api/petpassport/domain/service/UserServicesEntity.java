package api.petpassport.domain.service;

import api.petpassport.domain.user.UserEntity;
import api.petpassport.enums.PetTypeEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "user_services")
public class UserServicesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "pet_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private PetTypeEnum petType;

    @Column(name = "service_type", nullable = false)
    private String serviceType;

    @Column(name = "message")
    private String message;

    @Column(name = "departure")
    private boolean departure;

    @Column(name = "price_fixed")
    private Double priceFixed;

    @Column(name = "price_min")
    private Double priceMin;

    @Column(name = "price_max")
    private Double priceMax;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "update_at", nullable = false)
    private Instant updateAt;

    public static UserServicesEntity create(UserEntity user,
                                            String serviceType,
                                            PetTypeEnum petTypeEnum,
                                            String message,
                                            boolean departure,
                                            Double priceFixed,
                                            Double priceMin,
                                            Double priceMax) {
        UserServicesEntity userServicesEntity = new UserServicesEntity();
        userServicesEntity.setUser(user);
        userServicesEntity.setServiceType(serviceType);
        userServicesEntity.setPetType(petTypeEnum);
        userServicesEntity.setMessage(message);
        userServicesEntity.setDeparture(departure);
        userServicesEntity.setPriceFixed(priceFixed);
        userServicesEntity.setPriceMin(priceMin);
        userServicesEntity.setPriceMin(priceMax);
        userServicesEntity.setCreatedAt(Instant.now());
        userServicesEntity.setUpdateAt(Instant.now());
        return userServicesEntity;
    }

    public void update(String serviceTypeEnum,
                       PetTypeEnum petTypeEnum,
                       String message,
                       boolean departure,
                       Double priceFixed,
                       Double priceMin,
                       Double priceMax) {
        this.setServiceType(serviceTypeEnum);
        this.setPetType(petTypeEnum);
        this.setMessage(message);
        this.setDeparture(departure);
        this.setPriceFixed(priceFixed);
        this.setPriceMin(priceMin);
        this.setPriceMin(priceMax);
        this.setUpdateAt(Instant.now());
    }
}
