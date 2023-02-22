package ua.socialnetwork.security;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ua.socialnetwork.entity.Friend;
import ua.socialnetwork.entity.User;
import ua.socialnetwork.entity.UserImage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Slf4j
@AllArgsConstructor
public class SecurityUser implements UserDetails {
    private  User user;

    @Override
    //This method represents what users are allowed to do(authorities)
    //ToDo replace after with not static impl
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> roleList = new ArrayList<>();
        roleList.add(new SimpleGrantedAuthority("ROLE_" +user.getRole()));
        return roleList;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getFullName(){
        return user.getFirstName() + ' ' + user.getLastName();
    }
    public int getAge(){
        return user.getAge();
    }
    public String getFirstName(){
        return user.getFirstName();
    }
    public List<UserImage> getImages(){
        return user.getImages();
    }
    public int getImage() {
        return user.getImages().get(0).getId();
    }
    public int getId(){
        return user.getId();
    }

    //todo refactor
    public int getImageForFeed(){
        List<UserImage> out = user.getImages();

        if(out.size()==1 && out.get(0).getName().equals("userImage")){
            return out.get(0).getId();
        }
        if(out.size() == 2 && out.get(0).getName().equals("backgroundImage")){
            return out.get(1).getId();
        }
        if(out.size() == 2){
            return out.get(1).getId();
        }
        if(out.size() >= 3){
            return out.get(user.getImages().size() -1).getId();
        }
        return -1;

    }
//    public int getImageForFeed() {
//        List<UserImage> images = user.getImages();
//
//        if (images.size() == 1 && images.get(0).getName().equals("userImage")) {
//            return images.get(0).getId();
//        } else if (images.size() >= 2) {
//            int lastIndex = images.size() - 1;
//            if (images.get(0).getName().equals("backgroundImage")) {
//                return images.get(lastIndex).getId();
//            } else {
//                return images.get(1).getId();
//            }
//        }
//        return -1;
//    }

    public Set<Friend> getReceivedRequests(){
        return user.getReceivedRequests();
    }
    public Set<Friend> getSentRequest() {
        return user.getSentRequest();
    }

}
