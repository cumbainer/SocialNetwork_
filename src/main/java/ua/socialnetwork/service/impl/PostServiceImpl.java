package ua.socialnetwork.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.socialnetwork.entity.*;
import ua.socialnetwork.entity.enums.PostAction;
import ua.socialnetwork.repo.CommentReactionRepo;
import ua.socialnetwork.repo.CommentRepository;
import ua.socialnetwork.repo.PostRepo;
import ua.socialnetwork.service.PostService;


import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private PostRepo postRepo;

    private CommentRepository commentRepository;

    private CommentReactionRepo commentReactionRepo;

    @Override
    public Post create(Post post) {
        //TODO make validations and exc handler
//        log.info("A post " + post.toString() + " was created in PostServiceImpl");

//        post.setCreationDate(LocalDateTime.now());
        return postRepo.save(post);
    }

    @Override
    public Post create(Post post, MultipartFile postImage) {
        PostImage image;

        if (postImage.getSize() != 0) {
            image = toImageEntity(postImage);
            post.setImageToPost(image);
        }

        //TODO make validations and exc handler
        log.info("A post " + post.toString() + " was created in PostServiceImpl");

        post.setCreationDate(LocalDateTime.now());
        return postRepo.save(post);
    }

    @Override
    public Post update(Post post) {
        return null;
    }

    @Override
    public Post update(Post post, MultipartFile postImage) {
        PostImage image;

        if (postImage.getSize() != 0) {
            image = toImageEntity(postImage);
            post.setImageToPost(image);
        }


        post.setEditionDate(LocalDateTime.now());
        return postRepo.save(post);
    }


    @Override
    public Post readById(int id) {
        log.info("A post with id: " + id + " was read in PostServiceImpl");
        return postRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Post with id: " + id + "has not been found"));
    }

    @Override
    public void delete(int id) {

        postRepo.delete(readById(id));
    }

    @Override
    public List<Post> getAll() {
        //ToDO add validation and stuff later


        return postRepo.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Override
    public List<Post> getByUserId(int userId) {
        return null;
    }

    @Override
    public List<Post> getPostsByUser_Username(String username) {
        List<Post> posts = postRepo.getPostsByUser_Username(username, Sort.by(Sort.Direction.DESC, "id"));
        return posts;

    }

    @SneakyThrows
    private PostImage toImageEntity(MultipartFile postImage) {
        PostImage image = new PostImage();
        image.setName(postImage.getName());
        image.setOriginalFileName(postImage.getOriginalFilename());
        image.setContentType(postImage.getContentType());
        image.setSize(postImage.getSize());
        image.setBytes(postImage.getBytes());
        return image;
    }

    public void makeReaction(Post post, PostAction action) {
        int likeCounter = post.getLikeCounter();
        int dislikeCounter = post.getDislikeCounter();

        switch (action) {
            case LIKE -> {
                post.setLiked(true);
                likeCounter++;
                post.setLikeCounter(likeCounter);
                if (dislikeCounter != 0) {
                    dislikeCounter--;

                }
                post.setDislikeCounter(dislikeCounter);
                post.setDisliked(false);
                log.info("Post with id: " + post.getId() + " is liked");
            }
            case DISLIKE -> {
                post.setDisliked(true);
                dislikeCounter++;
                post.setDislikeCounter(dislikeCounter);
                if (likeCounter != 0) {
                    likeCounter--;

                }
                post.setLikeCounter(likeCounter);
                post.setLiked(false);
                log.info("Post with id: " + post.getId() + " is disliked");
            }
        }
    }

    public List postPreparation(User user) {
        List<Object> dataPosts = new ArrayList<>();

        int likeSum;
        int dislikeSum;
        boolean isLike;
        boolean isDislike;

        List<Post> posts = postRepo.findAll();
        for (Post post : posts) {
            List<Object> itemPost = new ArrayList<>();
            List<Object> itemComments = new ArrayList<>();
            itemPost.add(0, post);

            List<Comment> comments = commentRepository.findAllByPostId((int) post.getId());
            for (Comment comment : comments) {
                List<Object> itemComment = new ArrayList<>();
                itemComment.add(0, comment);

                likeSum = 0;
                dislikeSum = 0;
                isLike = false;
                isDislike = false;

                List<CommentReactions> reactions = commentReactionRepo.findAllByCommentId((int) comment.getId());
                for (CommentReactions reaction : reactions) {
                    if (reaction.getReaction() == true) {
                        likeSum++;
                    } else {
                        dislikeSum++;
                    }
                    if (reaction.getUser().getId() == user.getId()) {
                        if (reaction.getReaction() == true) {
                            isLike = true;
                        } else {
                            isDislike = true;
                        }
                    }
                }
                itemComment.add(1, likeSum);
                itemComment.add(2, dislikeSum);
                itemComment.add(3, isLike);
                itemComment.add(4, isDislike);
                itemComments.add(itemComment);
            }
            itemPost.add(1, itemComments);
            dataPosts.add(0, itemPost);
        }
        return dataPosts;
    }
}
