package ua.socialnetwork.service;

import ua.socialnetwork.entity.Friend;

public interface FriendService {

    Friend create(Friend friend, int senderId, int receiverId);

    void removeFromFriends(int friendEntityId);

    Friend getFriendByReceiverUsername(String receiverUsername, String senderUsername);
}
