package ua.socialnetwork.controller;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.socialnetwork.dto.PostDto;
import ua.socialnetwork.entity.Post;
import ua.socialnetwork.entity.enums.PostAction;
import ua.socialnetwork.security.SecurityUser;
import ua.socialnetwork.service.PostService;
import ua.socialnetwork.service.UserService;

import java.time.LocalDateTime;

@Controller
@AllArgsConstructor
@RequestMapping({"/posts", "/"})
@Slf4j
public class PostController {
    private final UserService userService;
    private final PostService postService;

    @GetMapping("/feed")
    public String getAll(@AuthenticationPrincipal SecurityUser authUser, Model model) {

        model.addAttribute("imageIsPresent", authUser.getImages().size() > 0);
        model.addAttribute("posts", postService.getAll());
        model.addAttribute("newPost", new Post());
        model.addAttribute("users", userService.getAll());

        return "feed";
    }

    @PreAuthorize("hasRole('ROLE_ADMIM') or authentication.principal.username == #username")
    @PostMapping("/new/{username}/")
    public String create(@PathVariable("username") String username, PostDto postDto,
                         @RequestParam(value = "postImage", required = false) MultipartFile postImage) {

        postDto.setUser(userService.readByUsername(username));

        postService.create(postDto, postImage);
        return "redirect:/feed";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.id == @postServiceImpl.readById(#postId).user.id")
    @GetMapping("/{post_id}/update/")
    public String editForm(@PathVariable("post_id") Integer postId, Model model) {

        model.addAttribute("post", postService.readById(postId));

        return "update-post";
    }

    @PreAuthorize("hasRole('ROLE_ADMIM') or #postDto.user.id == authentication.principal.id")
    @PostMapping("/update/")
    public String edit(PostDto postDto, BindingResult result, @RequestParam(value = "postImage", required = false) MultipartFile postImage) {

        if (result.hasErrors()) {
            log.warn("Binding result had an error in Post Controller update with post, id: " + postDto.getId());
            return "update-post";
        }

        postDto.setEditionDate(LocalDateTime.now());
        postService.update(postDto, postImage);

        return "redirect:/feed";
    }

    @PreAuthorize("hasRole('ROLE_ADMIM') or @postServiceImpl.readById(#postId).user.id == authentication.principal.id")
    @GetMapping("/{post_id}/delete/")
    public String delete(@PathVariable("post_id") Integer postId, @AuthenticationPrincipal SecurityUser authUser) {

        String username = authUser.getUsername();

        postService.delete(postId);

        return "redirect:/users/" + username;
    }

    @GetMapping("/{post_id}/like/")
    public String like(@PathVariable("post_id") Integer post_id) {

        PostDto postDto = postService.readById(post_id);

        postService.makeReaction(postDto, PostAction.LIKE);
        postService.create(postDto);
        return "redirect:/feed";
    }

    @GetMapping("/{post_id}/dislike/")
    public String dislike(@PathVariable("post_id") Integer post_id) {

        PostDto postDto = postService.readById(post_id);

        postService.makeReaction(postDto, PostAction.DISLIKE);
        postService.create(postDto);
        return "redirect:/feed";
    }

}
