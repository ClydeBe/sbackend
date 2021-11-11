package com.thewheel.sawatu.database.repository;

import com.thewheel.sawatu.database.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    public List<Post> findTop3IdByOrderByIdDesc();

    public Page<Post> findAllByAuthorNameOrderByIdDesc(@Param("username") String username, Pageable  pageable);
}