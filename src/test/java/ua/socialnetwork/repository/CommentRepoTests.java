//package ua.socialnetwork.repository;
//
//
//import lombok.AllArgsConstructor;
//import org.checkerframework.checker.units.qual.C;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.security.core.parameters.P;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import ua.socialnetwork.entity.Comment;
//import ua.socialnetwork.entity.Post;
//import ua.socialnetwork.repo.CommentRepository;
//import ua.socialnetwork.repo.PostRepo;
//
//import java.util.List;
//
//@DataJpaTest
//@ExtendWith(SpringExtension.class)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//public class CommentRepoTests {
//    @Autowired
//    private PostRepo postRepo;
//    @Autowired
//    private CommentRepository commentRepo;
//
//    private Comment getComment(){
//        Comment comment = new Comment();
//        comment.setText("fdsa");
//        return comment;
//    }
//
//    @Test
//    public void create(){
//        Post post = new Post();
//        Comment c = getComment();
//        c.setPost(post);
//        postRepo.save(post);
//        commentRepo.save(c);
//        List<Comment> comm = commentRepo.findAllByPostId(post.getId());
//        Assertions.assertNotEquals(0, comm.size());
//
//
//    }
//
//
//}
