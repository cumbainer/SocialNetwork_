package ua.socialnetwork.service;

import org.springframework.web.multipart.MultipartFile;
import ua.socialnetwork.dto.PostDto;
import ua.socialnetwork.entity.Post;
import ua.socialnetwork.entity.enums.PostAction;

import java.util.List;

public interface PostService {
    Post create(PostDto postDto);

    Post create(PostDto postDto, MultipartFile postImage);

    Post update(PostDto postDto, MultipartFile multipartFile);

    void delete(int id);

    PostDto readById(int id);

    List<PostDto> getPostsByUser_Username(String username);

    List<PostDto> getAll();

    void makeReaction(PostDto post, PostAction action);
}
