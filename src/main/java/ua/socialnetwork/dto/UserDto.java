package ua.socialnetwork.dto;


import lombok.Data;
import ua.socialnetwork.entity.Friend;
import ua.socialnetwork.entity.Post;
import ua.socialnetwork.entity.UserImage;
import ua.socialnetwork.entity.enums.Gender;

import java.util.List;

@Data
public class UserDto {
    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private int age;
    private String bio;
    private Gender gender;
    private String country;
    private boolean employed;
    private List<Friend> sentRequest;
    private List<Friend> receivedRequests;
    private List<Post> posts;
    private List<UserImage> images;
}
