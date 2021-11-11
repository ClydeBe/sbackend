package com.thewheel.sawatu.database.repository;

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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@RunWith(SpringRunner.class)
@DirtiesContext
class PostRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    @Test
    void findFirstByOrderByIdDesc_returnLastPosts() {
        // Given
        User user = User.builder()
                .role(Role.ADMIN)
                .username("be")
                .password("#pswd")
                .email("be@be.be")
                .isActive(true)
                .build();
        Post post_1 = Post.builder()
                .author(user)
                .build();
        Post post_2 = Post.builder()
                .author(user)
                .build();
        Post post_3 = Post.builder()
                .author(user)
                .build();
        Post post_4 = Post.builder()
                .author(user)
                .build();

        userRepository.saveAndFlush(user);
        postRepository.saveAndFlush(post_1);
        postRepository.saveAndFlush(post_2);
        postRepository.saveAndFlush(post_3);
        postRepository.saveAndFlush(post_4);

        // When
        List<Post> response = postRepository.findTop3IdByOrderByIdDesc();

        // Then
        assertThat(response)
                .isNotNull()
                .hasSize(3)
                .containsExactly(post_4, post_3, post_2);

    }

    @Test
    void findAllByAuthorNameOrderByIdDesc_shouldReturnCorrectList() {
        // Given
        User user = User.builder()
                .role(Role.ADMIN)
                .username("be")
                .password("#pswd")
                .email("be@be.be")
                .isActive(true)
                .build();
        Post post_1 = Post.builder()
                .author(user)
                .build();
        Post post_2 = Post.builder()
                .author(user)
                .build();
        Post post_3 = Post.builder()
                .author(user)
                .build();
        Post post_4 = Post.builder()
                .author(user)
                .build();

        userRepository.saveAndFlush(user);
        postRepository.saveAndFlush(post_1);
        postRepository.saveAndFlush(post_2);
        postRepository.saveAndFlush(post_3);
        postRepository.saveAndFlush(post_4);

        // When
        Page<Post> posts = postRepository.findAllByAuthorNameOrderByIdDesc("be", PageRequest.of(0, 4));

        // Then
        assertThat(posts)
                .isNotNull()
                .hasSize(4)
                .containsExactly(post_4, post_3, post_2, post_1);
    }

    @Test
    void findAllByAuthorNameOrderByIdDesc_shouldReturnEmptyList_ifTableIsEmpty() {
        // Given

        // When
        Page<Post> posts = postRepository.findAllByAuthorNameOrderByIdDesc("be", PageRequest.of(0, 4));

        // Then
        assertThat(posts)
                .isNotNull()
                .hasSize(0);
    }

}