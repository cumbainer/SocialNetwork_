package ua.socialnetwork.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ua.socialnetwork.entity.Friend;
import ua.socialnetwork.entity.User;
import ua.socialnetwork.entity.enums.FriendStatus;
import ua.socialnetwork.repo.FriendRepo;
import ua.socialnetwork.security.SecurityUser;
import ua.socialnetwork.service.FriendService;
import ua.socialnetwork.service.UserService;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
@Slf4j
@AllArgsConstructor
public class FriendServiceImpl implements FriendService {
    private FriendRepo friendRepo;
    private UserService userService;

    @Override
    public Friend create(Friend friend, int senderId, int receiverId) {
        User receiver = userService.readById(receiverId);
        User sender = userService.readById(senderId);

        receiver.getReceivedRequests().add(friend);
        sender.getSentRequest().add(friend);
        friend.setStatus(FriendStatus.FRIEND);
        friend.setSendingRequestDate(LocalDateTime.now());
        log.info("A friendship has created between [sender: " + sender.getId() + ", receiver: " + receiver.getId() + ']');
        return friendRepo.save(friend);
    }

    @Override
    public void removeFromFriends(int friendEntityId) {
        Friend friend = friendRepo.findById(friendEntityId).orElseThrow(
                () -> new NoSuchElementException("\"Caught an NoSuchElementException exception while deleting friend with id: \" + friendEntityId"));
        friendRepo.delete(friend);
    }

    @Override
    public Friend getFriendByReceiverUsername(String receiverUsername, String senderUsername) {
        return friendRepo.findFriendByReceiver_UsernameAndSender_Username(receiverUsername, senderUsername);
    }
}
