package ua.socialnetwork.dto;


import lombok.Getter;
import lombok.Setter;
import ua.socialnetwork.entity.Friend;
import ua.socialnetwork.entity.Post;
import ua.socialnetwork.entity.UserImage;
import ua.socialnetwork.entity.enums.Gender;

import java.util.List;

@Getter
@Setter
public class UserDto {

    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private int age;
    private Gender gender;
    private boolean employed;
    private List<Friend> sentRequests;
    private List<Friend> receivedRequests;
    private List<Post> posts;
    private List<UserImage> images;

}
