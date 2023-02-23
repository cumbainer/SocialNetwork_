package ua.socialnetwork.dto;

import ua.socialnetwork.entity.Comment;
import ua.socialnetwork.entity.User;

import java.util.List;

public class PostDto {
    private String body;
    private User user;
    private List<Comment> comments;
}
