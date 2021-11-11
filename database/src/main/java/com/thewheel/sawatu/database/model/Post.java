package com.thewheel.sawatu.database.model;

import lombok.*;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.EAGER;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_post", schema = "public",
    indexes = @Index(name = "post_author__idx", columnList = "author_id")
)
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
@Audited
@AuditTable(value = "audit_post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cover")
    private String cover;

    @Column(name = "title")
    private String title;

    @Column(name = "body", columnDefinition = "text")
    private String body;

    @Column(name = "tags")
    private String tags;

    @Column(name = "author_id", insertable = false, updatable = false)
    private String authorName;

    @CreatedBy
    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "author_id", foreignKey = @ForeignKey(name = "post_user__fk"), nullable = false)
    private User author;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    public void setUpdatedAt() {
        updatedAt = LocalDateTime.now();
    }
}

