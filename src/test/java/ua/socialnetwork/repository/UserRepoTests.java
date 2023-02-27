package ua.socialnetwork.repository;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.assertj.core.api.Assert;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.socialnetwork.SocialNetworkApplication;
import ua.socialnetwork.entity.User;
import ua.socialnetwork.repo.UserRepo;
import  org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepoTests {

    @Autowired
    private UserRepo userRepo;

    @Test
    public void testFindUserByValidUsername(){
        userRepo.save(getUser());
        User user = userRepo.findUserByUsername("username123").orElse(null);
        Assertions.assertNotNull(user);
        Assertions.assertNotEquals(0, user.getId());
    }

    @Test
    public void testFindUserByWrongUsername(){
        User user = userRepo.findUserByUsername("wrongUsenameString").orElse(null);
        Assertions.assertNull(user);
    }

    public User getUser(){
        User user = new User();
        user.setUsername("username123");
        user.setEmail("valid2@email.ua");
        user.setFirstName("Firstname");
        user.setLastName("Lastname");
        user.setPassword("qwQW12!@");
        return user;
    }

}
