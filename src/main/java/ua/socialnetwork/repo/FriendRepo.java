package ua.socialnetwork.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.socialnetwork.entity.Friend;
import ua.socialnetwork.entity.PostImage;

public interface FriendRepo extends JpaRepository<Friend, Integer> {
}
