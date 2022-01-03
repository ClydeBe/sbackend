package com.thewheel.sawatu.database.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comment")
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_generator")
    @SequenceGenerator(name = "comment_generator", sequenceName = "s_comment", allocationSize = 1)
    private Long id;

    @Column(name = "body", columnDefinition = "text")
    private String body;

    @Column(name = "author_id", insertable = false, updatable = false)
    private String authorName;

    @Column(name = "post_id", insertable = false, updatable = false)
    private Long postId;

    @Column(name = "reply_id", insertable = false, updatable = false)
    private Long replyId;

    @CreatedBy
    @ManyToOne(fetch = EAGER)
    @JsonBackReference
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne(fetch = LAZY)
    @JsonBackReference
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToMany(fetch = EAGER)
    @JoinColumn(name = "reply_id")
    private List<Comment> comments;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    public void setUpdatedAt() {
        updatedAt = LocalDateTime.now();
    }
}
