package ua.socialnetwork.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.socialnetwork.service.UserService;


@AllArgsConstructor
@Controller
@RequestMapping("/friend")
public class FriendController {

    UserService userService;



    @GetMapping("/add/{username}")
    public String addFriend(@PathVariable("username") String username) {


        return "redirect:/"+username;
    }




}
