package ua.socialnetwork.dto;

import lombok.Data;
import ua.socialnetwork.entity.Comment;
import ua.socialnetwork.entity.PostImage;
import ua.socialnetwork.entity.User;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostDto {
    private int id;
    private String body;
    private UserDto user;
    private List<Comment> comments;
    private LocalDateTime creationDate;
    private LocalDateTime editionDate;
    private PostImage image;
    private int likeCounter;
    private int dislikeCounter;
    private boolean liked;
    private boolean disliked;
}
