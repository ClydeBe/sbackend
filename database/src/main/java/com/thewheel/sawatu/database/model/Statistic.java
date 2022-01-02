package com.thewheel.sawatu.database.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.EAGER;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_statistics",
        indexes = {
                @Index(name = "user_statistics__idx", columnList = "user_id")
        }
)
public class Statistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "haircut_count")
    private int haircutCount;

    @Column(name = "followers_count")
    private int followersCount;

    @Column(name = "rate")
    private int rate;

    @Column(name = "user_id", insertable = false, updatable = false)
    private String userName;

    @OneToOne(fetch = EAGER)
    @JsonBackReference
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "statistics_user__fk"))
    private User user;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    public void setUpdatedAt() {
        updatedAt = LocalDateTime.now();
    }
}
