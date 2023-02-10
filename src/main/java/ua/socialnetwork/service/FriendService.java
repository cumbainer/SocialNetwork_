package ua.socialnetwork.service;

import ua.socialnetwork.entity.Friend;
import ua.socialnetwork.entity.User;

public interface FriendService {
    //ToDO add methods

    Friend create(Friend friend, int senderId,int receiverId);

    void removeFromFriends(int friendEntityId);
    Friend getFriendByReceiverUsername(String receiverUsername, String senderUsername);
}
