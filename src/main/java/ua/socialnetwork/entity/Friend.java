package ua.socialnetwork.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.socialnetwork.entity.enums.FriendStatus;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "friend")
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private FriendStatus status;

    @Column(name = "sendingRequestDate")
    private LocalDateTime sendingRequestDate;

    @Column(name = "acceptingRequestDate")
    private LocalDateTime acceptingRequestDate;

    @Column(name = "decliningRequestDate")
    private LocalDateTime decliningRequestDate;

}
