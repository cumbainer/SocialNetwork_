package ua.socialnetwork.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.socialnetwork.entity.Friend;
import ua.socialnetwork.entity.User;
import ua.socialnetwork.security.SecurityUser;
import ua.socialnetwork.service.FriendService;
import ua.socialnetwork.service.UserService;

import java.util.List;
import java.util.Set;


@AllArgsConstructor
@Controller
@RequestMapping("/friend")
public class FriendController {
    private UserService userService;
    private FriendService friendService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.username == #senderUsername")
    @GetMapping("{sender_username}/add/{receiver_username}")
    public String addFriend(@PathVariable("sender_username") String senderUsername,
                            @PathVariable("receiver_username") String receiverUsername, HttpServletRequest request,
                            Model model) {

        String requestURL = request.getRequestURI();
        model.addAttribute("requestURL", requestURL);

        User receiver = userService.readByUsername(receiverUsername);
        User sender = userService.readByUsername(senderUsername);

        Friend friend = new Friend();
        friend.setSender(sender);
        friend.setReceiver(receiver);

        friendService.create(friend,sender.getId(), receiver.getId());

        return "redirect:/feed";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.id == @friendServiceImpl.getById(#friendId).sender.id")
    @GetMapping("/{friendId}/delete")
    public String deleteFromFriend(@PathVariable("friendId") Integer friendId ){

        friendService.removeFromFriends(friendId);

        return "redirect:/feed";
    }

    @GetMapping("/notification")
    public String getNotification(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser u = (SecurityUser) authentication.getPrincipal();

        Set<Friend> receivedRequests = u.getReceivedRequests();
        model.addAttribute("requests", receivedRequests);


        return "notifications";
    }

}
