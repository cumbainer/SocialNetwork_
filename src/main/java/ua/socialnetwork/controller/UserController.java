package ua.socialnetwork.controller;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.socialnetwork.entity.Friend;
import ua.socialnetwork.entity.Post;
import ua.socialnetwork.entity.User;
import ua.socialnetwork.exception.UserAlreadyExistsException;
import ua.socialnetwork.security.SecurityUser;
import ua.socialnetwork.service.FriendService;
import ua.socialnetwork.service.PostService;
import ua.socialnetwork.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/users")
@Slf4j
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final PostService postService;
    private final FriendService friendService;

    @GetMapping("/create")
    public String create(Model model) {

        model.addAttribute("user", new User());

        return "create-user";
    }

    @PostMapping("/create")
    public String create(@Validated @ModelAttribute("user") User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "create-user";
        }

        try {
            userService.create(user);

        } catch (UserAlreadyExistsException ex) {
            log.warn("UserAlreadyExistsException is caught in UserController with mesasge: " + ex.getMessage() + " and" +
                    "cause " + ex.getCause());
            model.addAttribute("message", "An account for that username/email already exists.");
            return "login-page";
        }

        return "redirect:/users/create/continue/" + user.getId();
    }

    @GetMapping("/{user_id}/update/")
    public String updateForm(@PathVariable("user_id") Integer user_id, Model model) {
        User user = userService.readById(user_id);

        model.addAttribute("user", user);

        return "update-user";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.id == #user.id")
    @PostMapping("/update")
    public String update(@Validated User user, @RequestParam(value = "userImage", required = false) MultipartFile userImage,
                         BindingResult result) {
        if (result.hasErrors()) {
            log.warn("Binding result had an error in User Controller update with user: " + user.getUsername());
            return "update-user";
        }
        userService.update(user, userImage);
        log.info("User with id: " + user.getId() + " has been updated");
        return "redirect:/users/" + user.getUsername();
    }


    @GetMapping("/create/continue/{user_id}")
    public String createSecondaryInfoForm(@PathVariable("user_id") Integer user_id, Model model) {
        User user = userService.readById(user_id);

        model.addAttribute("user", user);

        return "create-secondaryInfo";
    }

    @PostMapping("/create/continue/{user_id}")
    public String createSecondaryInfo(@PathVariable("user_id") Integer id, @Validated User user,
                                      @RequestParam(value = "userImage", required = false) MultipartFile userImage,
                                      @RequestParam(value = "imageBackground", required = false) MultipartFile imageBackground,
                                      BindingResult result) {

        if (result.hasErrors()) {

            return "create-secondaryInfo";
        }

        User oldUser = userService.readById(id);

        user.setFirstName(oldUser.getFirstName());
        user.setLastName(oldUser.getLastName());
        user.setUsername(oldUser.getUsername());
        user.setEmail(oldUser.getEmail());

        userService.update(user, userImage, imageBackground);

        return "redirect:/login";
    }

    @GetMapping("/search")
    public String getAll(Model model) {
        model.addAttribute("users", userService.getAll());

        return "Searchbar";
    }

    @GetMapping("/{username}")
    public String getUser(@PathVariable("username") String username, @AuthenticationPrincipal SecurityUser authUser, Model model) {
        User user = userService.readByUsername(username);
        List<Post> posts = postService.getPostsByUser_Username(username);

        Friend friend = friendService.getFriendByReceiverUsername(username, authUser.getUsername());

        model.addAttribute("ifFriend", authUser.getReceivedRequests().contains(friend) || friend != null);
        model.addAttribute("isAccount", authUser.getUsername().equals(username));
        model.addAttribute("imageIsPresent", authUser.getImages().size() > 0);
        model.addAttribute("posts", posts);
        model.addAttribute("user", user);
        model.addAttribute("image", user.getImages());
        model.addAttribute("size", user.getImages().size());

        return "profile-page";
    }

    @GetMapping("/{username}/friends")
    public String getFriendList(@PathVariable("username") String username, @AuthenticationPrincipal SecurityUser authUser, Model model) {
        User user = userService.readByUsername(username);

        model.addAttribute("imageIsPresent", authUser.getImages().size() > 0);
        model.addAttribute("users", userService.getAll());

        model.addAttribute("user", user);
        model.addAttribute("image", user.getImages());

        model.addAttribute("size", user.getImages().size());
        return "friend-list";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.id == #id")
    @GetMapping("/{user_id}/delete")
    public String deleteUser(@PathVariable("user_id") Integer id) {
        userService.delete(id);
        return "redirect:/feed";
    }

}
