package ua.socialnetwork.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.socialnetwork.dto.FriendDto;
import ua.socialnetwork.dto.UserDto;
import ua.socialnetwork.service.FriendService;
import ua.socialnetwork.service.UserService;


@AllArgsConstructor
@Controller
@RequestMapping("/friend")
public class FriendController {
    private UserService userService;
    private FriendService friendService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.username == #senderUsername")
    @GetMapping("{sender_username}/add/{receiver_username}")
    public String addFriend(@PathVariable("sender_username") String senderUsername,
                            @PathVariable("receiver_username") String receiverUsername) {

        UserDto receiver = userService.readByUsername(receiverUsername);
        UserDto sender = userService.readByUsername(senderUsername);

        FriendDto friend = new FriendDto();
        friend.setSender(sender);
        friend.setReceiver(receiver);

        friendService.create(friend, sender.getId(), receiver.getId());

        return "redirect:/feed";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.id == @friendServiceImpl.getById(#friendId).sender.id")
    @GetMapping("/{friendId}/delete")
    public String deleteFromFriend(@PathVariable("friendId") Integer friendId) {
        friendService.removeFromFriends(friendId);

        return "redirect:/feed";
    }

}
