package api.petpassport.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserServiceRequestsRepository extends JpaRepository<UserServiceRequestsEntity, UUID> {

    Page<UserServiceRequestsEntity> findByUserIdOrderByCreatedAtDesc(UUID userId, Pageable pageable);

    @Query("""
            select usr from UserServiceRequestsEntity usr
            where usr.userService.user.id = :userId
            """)
    Page<UserServiceRequestsEntity> findForExecutorUserIdOrderByCreatedAtDesc(
            @Param(value = "userId") UUID userId, Pageable pageable);

}
