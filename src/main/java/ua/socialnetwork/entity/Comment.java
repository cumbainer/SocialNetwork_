package ua.socialnetwork.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User createdBy;

    @Column(name = "text")
    private String text;

    @Column(name = "createdDate")
    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
}