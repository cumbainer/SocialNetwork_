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
import ua.socialnetwork.dto.FriendDto;
import ua.socialnetwork.dto.UserDto;
import ua.socialnetwork.entity.User;
import ua.socialnetwork.exception.UserAlreadyExistsException;
import ua.socialnetwork.security.SecurityUser;
import ua.socialnetwork.service.FriendService;
import ua.socialnetwork.service.PostService;
import ua.socialnetwork.service.UserService;

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
    public String create(@Validated @ModelAttribute("user") UserDto userDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "create-user";
        }

        try {
            userService.create(userDto);

        } catch (UserAlreadyExistsException ex) {
            log.warn("UserAlreadyExistsException is caught in UserController with mesasge: " + ex.getMessage() + " and" +
                    "cause " + ex.getCause());
            model.addAttribute("message", "An account for that username/email already exists.");
            return "login-page";
        }

        return "redirect:/users/create/continue/" + userDto.getId();
    }

    @GetMapping("/{user_id}/update/")
    public String updateForm(@PathVariable("user_id") Integer user_id, Model model) {

        model.addAttribute("user", userService.readById(user_id));

        return "update-user";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.id == #userDto.id")
    @PostMapping("/update")
    public String update(@Validated UserDto userDto, @RequestParam(value = "userImage", required = false) MultipartFile userImage,
                         BindingResult result) {
        if (result.hasErrors()) {
            log.warn("Binding result had an error in User Controller update with user: " + userDto.getUsername());
            return "update-user";
        }
        userService.update(userDto, userImage);
        log.info("User with id: " + userDto.getId() + " has been updated");
        return "redirect:/users/" + userDto.getUsername();
    }


    @GetMapping("/create/continue/{user_id}")
    public String createSecondaryInfoForm(@PathVariable("user_id") Integer user_id, Model model) {

        model.addAttribute("user", userService.readById(user_id));

        return "create-secondaryInfo";
    }

    @PostMapping("/create/continue/{user_id}")
    public String createSecondaryInfo(@PathVariable("user_id") Integer id, @Validated UserDto userDto,
                                      @RequestParam(value = "userImage", required = false) MultipartFile userImage,
                                      @RequestParam(value = "imageBackground", required = false) MultipartFile imageBackground,
                                      BindingResult result) {

        if (result.hasErrors()) {

            return "create-secondaryInfo";
        }

        UserDto oldUser = userService.readById(id);

        userDto.setFirstName(oldUser.getFirstName());
        userDto.setLastName(oldUser.getLastName());
        userDto.setUsername(oldUser.getUsername());
        userDto.setEmail(oldUser.getEmail());

        userService.update(oldUser, userImage, imageBackground);

        return "redirect:/login";
    }

    @GetMapping("/search")
    public String getAll(Model model) {
        model.addAttribute("users", userService.getAll());

        return "Searchbar";
    }

    @GetMapping("/{username}")
    public String getUser(@PathVariable("username") String username, @AuthenticationPrincipal SecurityUser authUser, Model model) {
        UserDto user = userService.readByUsername(username);

        FriendDto friend = friendService.getFriendByReceiverUsername(username, authUser.getUsername());

        model.addAttribute("ifFriend", friend != null);
        model.addAttribute("isAccount", authUser.getUsername().equals(username));
        model.addAttribute("imageIsPresent", authUser.getImages().size() > 0);
        model.addAttribute("posts", postService.getPostsByUser_Username(username));
        model.addAttribute("user", userService.readByUsername(username));
        model.addAttribute("image", user.getImages());
        model.addAttribute("size", user.getImages().size());

        return "profile-page";
    }

    @GetMapping("/{username}/friends")
    public String getFriendList(@PathVariable("username") String username, @AuthenticationPrincipal SecurityUser authUser, Model model) {
        UserDto userDto = userService.readByUsername(username);

        model.addAttribute("imageIsPresent", authUser.getImages().size() > 0);
        model.addAttribute("users", userService.getAll());

        model.addAttribute("user", userDto);
        model.addAttribute("image", userDto.getImages());

        model.addAttribute("size", userDto.getImages().size());
        return "friend-list";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.id == #id")
    @GetMapping("/{user_id}/delete")
    public String deleteUser(@PathVariable("user_id") Integer id) {
        userService.delete(id);
        return "redirect:/feed";
    }

}
