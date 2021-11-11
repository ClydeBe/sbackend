package com.thewheel.sawatu.database.repository;

import com.thewheel.sawatu.database.model.Comment;
import com.thewheel.sawatu.database.model.Post;
import com.thewheel.sawatu.database.model.Role;
import com.thewheel.sawatu.database.model.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext
class CommentRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Test
    void findCommentsByPostIdOrderByIdDesc_shouldReturnCorrectListOfComments() {
        // Given
        User user = User.builder()
                .isActive(true)
                .email("be@be.be")
                .password("#pswd")
                .username("be")
                .role(Role.ADMIN)
                .build();
        user = userRepository.saveAndFlush(user);

        Post post_1 = Post.builder()
                .author(user)
                .body("My name is Joe")
                .build();
        Post post_2 = Post.builder()
                .author(user)
                .body("My name is Joe")
                .build();

        Comment comment_1 = Comment.builder()
                .author(user)
                .post(post_1)
                .body("Body of the comment")
                .build();
        Comment comment_2 = Comment.builder()
                .author(user)
                .post(post_1)
                .body("Body of the comment")
                .build();
        Comment comment_3 = Comment.builder()
                .author(user)
                .post(post_2)
                .body("Body of the comment")
                .build();
        postRepository.saveAndFlush(post_1);
        postRepository.saveAndFlush(post_2);
        commentRepository.saveAndFlush(comment_1);
        commentRepository.saveAndFlush(comment_2);
        commentRepository.saveAndFlush(comment_3);

        // When
        Page<Comment> response_1 = commentRepository
                .findCommentsByPostIdOrderByIdDesc(1L, PageRequest.of(0, 10));
        Page<Comment> response_2 = commentRepository
                .findCommentsByPostIdOrderByIdDesc(2L, PageRequest.of(0, 10));

        // Then
        assertThat(response_1)
                .isNotNull()
                .hasSize(2)
                .containsExactly(comment_2, comment_1);

        assertThat(response_2)
                .isNotNull()
                .hasSize(1)
                .containsExactly(comment_3);
    }

    @Test
    void findCommentsByPostIdOrderByIdDesc_shouldReturnEmptyListOfComments_ifTableInDatabaseEmpty() {
        // Given

        // When
        Page<Comment> response = commentRepository
                .findCommentsByPostIdOrderByIdDesc(4L, PageRequest.of(0, 10));

        // Then
        assertThat(response)
                .isNotNull()
                .hasSize(0);

    }
}