package ua.socialnetwork.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.socialnetwork.dto.PostDto;
import ua.socialnetwork.entity.Post;
import ua.socialnetwork.entity.PostImage;
import ua.socialnetwork.entity.enums.PostAction;
import ua.socialnetwork.repo.PostRepo;
import ua.socialnetwork.service.PostService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class PostServiceImpl implements PostService {
    private PostRepo postRepo;
    private ModelMapper modelMapper;

    @Override
    public Post create(PostDto postDto) {
        Post post = modelMapper.map(postDto, Post.class);
        return postRepo.save(post);
    }

    @Override
    public Post create(PostDto postDto, MultipartFile postImage) {
        PostImage image;

        Post post = modelMapper.map(postDto, Post.class);

        if (postImage.getSize() != 0) {
            image = toImageEntity(postImage);
            post.setImageToPost(image);
        }

        log.info("A post with id: " + post.getId() + " was created");

        post.setCreationDate(LocalDateTime.now());
        return postRepo.save(post);
    }

    @Override
    public Post update(PostDto postDto, MultipartFile postImage) {
        PostImage image;
        Post post = modelMapper.map(postDto, Post.class);

        if (postImage.getSize() != 0) {
            image = toImageEntity(postImage);
            post.setImageToPost(image);
        }

        log.info("A post with id: " + post.getId() + " was updated");

        post.setEditionDate(LocalDateTime.now());
        return postRepo.save(post);
    }

    @Override
    public PostDto readById(int id) {
        return postRepo.findById(id).map(post -> modelMapper.map(post, PostDto.class)).orElseThrow(
                () -> new EntityNotFoundException("Post with id: " + id + "has not been found"));
    }

    @Override
    public void delete(int id) {
        if (id != 0) {
            postRepo.deleteById(id);
            log.info("A post with id" + id + "has been deleted");
        }
        log.error("An error occurred in Post deletion with id: " + id);
    }

    @Override
    public List<PostDto> getAll() {
        return postRepo.findAll(Sort.by(Sort.Direction.DESC, "id")).stream()
                .map(post -> modelMapper.map(post, PostDto.class)).toList();
    }

    @Override
    public List<PostDto> getPostsByUser_Username(String username) {
        return postRepo.getPostsByUser_Username(username, Sort.by(Sort.Direction.DESC, "id")).stream()
                .map(post -> modelMapper.map(post, PostDto.class)).toList();
    }

    private PostImage toImageEntity(MultipartFile postImage) {
        PostImage image = new PostImage();
        image.setName(postImage.getName());
        image.setOriginalFileName(postImage.getOriginalFilename());
        image.setContentType(postImage.getContentType());
        image.setSize(postImage.getSize());
        try {
            image.setBytes(postImage.getBytes());

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return image;
    }

    //Needed to put like/dislike on a post
    public void makeReaction(PostDto postDto, PostAction action) {

        int likeCounter = postDto.getLikeCounter();
        int dislikeCounter = postDto.getDislikeCounter();

        switch (action) {
            case LIKE -> {
                postDto.setLiked(true);
                likeCounter++;
                postDto.setLikeCounter(likeCounter);
                if (dislikeCounter != 0) {
                    dislikeCounter--;

                }
                postDto.setDislikeCounter(dislikeCounter);
                postDto.setDisliked(false);
                log.info("Post with id: " + postDto.getId() + " is liked");
            }
            case DISLIKE -> {
                postDto.setDisliked(true);
                dislikeCounter++;
                postDto.setDislikeCounter(dislikeCounter);
                if (likeCounter != 0) {
                    likeCounter--;

                }
                postDto.setLikeCounter(likeCounter);
                postDto.setLiked(false);
                log.info("Post with id: " + postDto.getId() + " is disliked");
            }
        }

    }

}
