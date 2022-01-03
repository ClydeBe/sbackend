package com.thewheel.sawatu.database.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.EAGER;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "profile")
public class Profile {

    @Column(name = "haircut_count")
    private int haircutCount;

    @Column(name = "followers_count")
    private int followersCount;

    @Column(name = "rate")
    private int rate;

    @Id
    @Column(name = "user_id", insertable = false, updatable = false)
    private String userName;

    @OneToOne(fetch = EAGER)
    @JsonBackReference
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    public void setUpdatedAt() {
        updatedAt = LocalDateTime.now();
    }

}
