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
    @PostMapping("/new/{username}")
    public String create(@PathVariable("username") String username, Post post,
                         @RequestParam(value = "postImage", required = false) MultipartFile postImage) {
        post.setUser(userService.readByUsername(username));

        postService.create(post, postImage);
        return "redirect:/feed";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.id == @postServiceImpl.readById(#postId).user.id")
    @GetMapping("/{post_id}/update/")
    public String editForm(@PathVariable("post_id") Integer postId, Model model) {
        Post post = postService.readById(postId);

        model.addAttribute("post", post);

        return "update-post";
    }

    @PreAuthorize("hasRole('ROLE_ADMIM') or #post.user.id == authentication.principal.id")
    @PostMapping("/update/")
    public String edit(Post post, BindingResult result, @RequestParam(value = "postImage", required = false) MultipartFile postImage) {

        if (result.hasErrors()) {
            log.warn("Binding result had an error in Post Controller update with post, id: " + post.getId());
            return "update-post";
        }

        log.info("A post has been edited " + post.getId());
        postService.update(post, postImage);
        post.setEditionDate(LocalDateTime.now());

        return "redirect:/feed";
    }

    @PreAuthorize("hasRole('ROLE_ADMIM') or @postServiceImpl.readById(#postId).user.id == authentication.principal.id")
    @DeleteMapping("/{post_id}/delete/")
    public String delete(@PathVariable("post_id") Integer postId, @AuthenticationPrincipal SecurityUser authUser) {

        String username = authUser.getUsername();

        postService.delete(postId);

        return "redirect:/users/" + username;
    }

    @GetMapping("/{post_id}/like/")
    public String like(@PathVariable("post_id") Integer post_id) {

        Post post = postService.readById(post_id);

        postService.makeReaction(post, PostAction.LIKE);
        postService.create(post);
        return "redirect:/feed";
    }

    @GetMapping("/{post_id}/dislike/")
    public String dislike(@PathVariable("post_id") Integer post_id) {

        Post post = postService.readById(post_id);

        postService.makeReaction(post, PostAction.DISLIKE);
        postService.create(post);
        return "redirect:/feed";
    }

}
