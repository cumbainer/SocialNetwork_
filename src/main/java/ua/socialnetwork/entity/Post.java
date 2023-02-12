package ua.socialnetwork.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private int id;

    @Column(name = "body")
    private String body;

    //TODO make like/dislike attached to an user

    @Column(name = "liked")
    private boolean liked;
    @Column(name = "disliked")
    private boolean disliked;


    @Column(name = "likeCounter")
    private int likeCounter;

    @Column(name = "dislikeCounter")
    private int dislikeCounter;

    @Column(name = "creationDate")
    private LocalDateTime creationDate;

    @Column(name = "editionDate")
    @UpdateTimestamp
    private LocalDateTime editionDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @OneToOne(cascade = CascadeType.ALL)
    private PostImage image;

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", body='" + body + '\'' +
                ", liked=" + liked +
                ", disliked=" + disliked +
                ", creationDate=" + creationDate +
                ", editionDate=" + editionDate +
                ", comments=" + comments +
                '}';
    }

    public void setImageToPost(PostImage userImage){

        userImage.setPost(this);
        image = userImage;
    }

}
