package ua.socialnetwork.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ua.socialnetwork.entity.Comment;
import ua.socialnetwork.repo.CommentRepository;
import ua.socialnetwork.security.SecurityUser;
import ua.socialnetwork.service.CommentService;

import java.security.Principal;
import java.security.Security;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;

    public Comment create(Comment comment) {
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByPostId(int post_id) {
        return commentRepository.findAllByPostId(post_id);
    }

    @Override
    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    @Override
    public Comment readById(int comment_id) {
        return commentRepository.findById(comment_id).orElseThrow(() -> new NullPointerException("comment not found"));
    }

}
