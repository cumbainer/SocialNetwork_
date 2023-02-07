package ua.socialnetwork.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.socialnetwork.entity.enums.FriendStatus;
import ua.socialnetwork.service.impl.FriendService;

import java.time.LocalDateTime;

@Entity(name = "friend")
@Data
@NoArgsConstructor
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
    @Enumerated
    private FriendStatus status;

    @Column(name = "sendingRequestDate")
    private LocalDateTime sendingRequestDate;

    @Column(name = "acceptingRequestDate")
    private LocalDateTime acceptingRequestDate;

    @Column(name = "decliningRequestDate")
    private LocalDateTime decliningRequestDate;

}
