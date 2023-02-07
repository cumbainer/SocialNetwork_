package ua.socialnetwork.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.socialnetwork.entity.Friend;
import ua.socialnetwork.entity.User;
import ua.socialnetwork.service.FriendService;
import ua.socialnetwork.service.UserService;

import java.util.List;


@AllArgsConstructor
@Controller
@RequestMapping("/friend")
public class FriendController {

    private UserService userService;
    private FriendService friendService;



    @GetMapping("{sender_username}/add/{receiver_username}")
    public String addFriend(@PathVariable("sender_username") String senderUsername,
                            @PathVariable("receiver_username") String receiverUsername, HttpServletRequest request,
                            Model model) {

        String requestURL = request.getRequestURI();
        model.addAttribute("requestURL", requestURL);


        User receiver = userService.readByUsername(senderUsername);
        User sender = userService.readByUsername(receiverUsername);


        Friend friend = new Friend();
        friend.setSender(sender);
        friend.setReceiver(receiver);


        List<Friend> friends = sender.getSentRequest();


        friendService.create(friend, sender.getId());






        return "redirect:/feed";
    }







}
