package ua.socialnetwork.service;

import org.springframework.web.multipart.MultipartFile;
import ua.socialnetwork.entity.User;
import ua.socialnetwork.security.SecurityUser;

import java.util.*;

public interface UserService {

    User create(User user);
    User create(User user, MultipartFile userImage);
    User create(User user, MultipartFile userImage, MultipartFile imageBackground);
    User update(User user, MultipartFile userImage);
    User update(User user, MultipartFile userImage, MultipartFile imageBackground);
    void delete(int id);
    SecurityUser getSecurityUser();
    User readById(int id);
    User readByUsername(String username);

    List<User> getAll();




}
