package com.thewheel.sawatu.database.repository;

import com.thewheel.sawatu.database.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c WHERE c.postId = :postId GROUP BY c.replyId, c.id ORDER BY c.id DESC")
    public Page<Comment> findCommentsByPostIdOrderByIdDesc(@Param("postId") Long postId, Pageable pageable);
}