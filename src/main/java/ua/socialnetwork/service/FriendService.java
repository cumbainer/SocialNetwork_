package ua.socialnetwork.service;

import ua.socialnetwork.entity.Friend;

public interface FriendService {
    //ToDO add methods

    Friend create(Friend friend, int senderId,int receiverId);

    void removeFromFriends(int friendEntityId);
}
