package com.thewheel.sawatu.database.model;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_reviews",
    indexes = {
        @Index(name = "self_review__idx", columnList = "self")
    }
)
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
public class Reviews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "comment", nullable = false, columnDefinition = "text")
    private String comment;

    @Column(name = "reviewer", insertable = false, updatable = false)
    private String reviewerName;

    @Column(name = "self", insertable = false, updatable = false)
    private String selfName;

    @CreatedBy
    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "reviewer", foreignKey = @ForeignKey(name = "review_reviewer__fk"))
    private User reviewer;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "self", foreignKey = @ForeignKey(name = "review_user__fk"))
    private User self;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    public void setUpdatedAt() {
        updatedAt = LocalDateTime.now();
    }
}
