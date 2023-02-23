package ua.socialnetwork.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
//@NoArgsConstructor
@Table(name = "comments")
public class Comment {
    public Long getId() {
        return id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User createdBy;

    @Column(name = "text")
    private String text;

    @Column(name = "email")
    private String email;

    @Column(name = "liked")
    private boolean liked;

    @Column(name = "disliked")
    private boolean disliked;

    @Column(name = "createdDate")
    private LocalDateTime createdDate;

    @Column(name = "editedDate")
    private LocalDateTime editedDate;

    @Column(name = "deletedDate")
    private LocalDateTime deletedDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
}