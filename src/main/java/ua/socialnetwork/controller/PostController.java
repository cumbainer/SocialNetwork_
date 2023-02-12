package ua.socialnetwork.controller;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.socialnetwork.security.SecurityUser;
import ua.socialnetwork.entity.Post;
import ua.socialnetwork.entity.enums.PostAction;
import ua.socialnetwork.service.PostService;
import ua.socialnetwork.service.UserService;

import java.time.LocalDateTime;

@Controller
@RequestMapping({"/posts", "/"})
@Slf4j
public class PostController {
    private final UserService userService;
    private final PostService postService;


    public PostController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping("/feed")
    public String getAll(Model model){
        boolean ifImageIsPresent = false;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser u = (SecurityUser) authentication.getPrincipal();

        if(u.getImages().size() > 0){
            ifImageIsPresent = true;
        }

        model.addAttribute("imageIsPresent", ifImageIsPresent);
        model.addAttribute("posts", postService.getAll());
        model.addAttribute("newPost", new Post());
        model.addAttribute("users", userService.getAll());

        model.addAttribute("auth", authentication);

        return "feed";
    }

    @PostMapping("/new/{username}")
    public String create(@PathVariable("username") String username, Post post,
                            @RequestParam(value = "postImage", required = false) MultipartFile postImage, BindingResult result){
        post.setUser(userService.readByUsername(username));

        postService.create(post, postImage);
        log.info("From PostController a Post has been created, id: " + post.getId());
        return "redirect:/feed";
    }


    @GetMapping("/update/{post_id}")
    public String editForm(@PathVariable("post_id") Integer id, Model model){
        Post post = postService.readById(id);

        model.addAttribute("post", post);

        return "update-post";
    }

    @PostMapping("/update")
    public String edit(Post post, BindingResult result, @RequestParam(value = "postImage", required = false) MultipartFile postImage ){

        if(result.hasErrors()){
            log.warn("Binding result had an error in Post Controller update with post, id: " + post.getId());

            return "update-post";
        }

        log.info("A post has been edited " + post.getId());
        postService.update(post, postImage);
        post.setEditionDate(LocalDateTime.now());

        return "redirect:/feed";
    }
    @GetMapping("/delete/{post_id}")
    public String delete(@PathVariable("post_id") Integer post_id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser u = (SecurityUser) authentication.getPrincipal();

        String username = u.getUsername();

        postService.delete(post_id);

        return "redirect:/users/"+username;
    }



    @GetMapping("/like/{post_id}")
    public String like(@PathVariable("post_id") Integer post_id, Model model){

        //TODO fix likeCounter (attach to a post , the bug is each every post gets all previous post likes)
        Post post = postService.readById(post_id);

        postService.makeReaction(post, PostAction.LIKE);


        postService.create(post);
        return "redirect:/feed";
    }



    @GetMapping("/dislike/{post_id}")
    public String dislike(@PathVariable("post_id") Integer post_id, Model model){

        //TODO fix likeCounter (attach to a post , the bug is each every post gets all previous post likes)
        Post post = postService.readById(post_id);

        postService.makeReaction(post, PostAction.DISLIKE);
        postService.create(post);
        return "redirect:/feed";
    }


}
