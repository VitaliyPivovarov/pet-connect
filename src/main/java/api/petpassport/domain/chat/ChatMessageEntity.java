package api.petpassport.domain.chat;

import api.petpassport.domain.user.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "chat_messages")
public class ChatMessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "owner_user_id", nullable = false)
    private UserEntity user;

    @Column(name = "chat_id", nullable = false)
    private UUID chatId;

    @Column(name = "value", nullable = false)
    private String value;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "update_at", nullable = false)
    private Instant updateAt;

}
