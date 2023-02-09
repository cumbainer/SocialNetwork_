package ua.socialnetwork.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ua.socialnetwork.entity.Friend;
import ua.socialnetwork.entity.User;
import ua.socialnetwork.repo.FriendRepo;
import ua.socialnetwork.repo.UserRepo;
import ua.socialnetwork.security.SecurityUser;
import ua.socialnetwork.service.FriendService;
import ua.socialnetwork.service.UserService;

@Service
@AllArgsConstructor
public class FriendServiceImpl implements FriendService {

    private FriendRepo friendRepo;
    private UserService userService;

    @Override
    public Friend create(Friend friend, int senderId, int receiverId) {
        //Todo think of is later

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser u = (SecurityUser) authentication.getPrincipal();




        User receiver = userService.readById(receiverId);
        User sender = userService.readById(senderId);

        receiver.getReceivedRequests().add(friend);
        sender.getSentRequest().add(friend);

        return friendRepo.save(friend);

    }

    @Override
    public void removeFromFriends(int friendEntityId) {
        Friend friend = friendRepo.findById(friendEntityId).orElse(null);

        friendRepo.delete(friend);

    }
}
