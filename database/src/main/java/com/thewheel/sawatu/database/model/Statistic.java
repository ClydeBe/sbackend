package com.thewheel.sawatu.database.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "statistic")
public class Statistic {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "statistic_generator")
    @SequenceGenerator(name = "statistic_generator", sequenceName = "s_statistic", allocationSize = 1)
    private Long id;

    @Column(name = "profile")
    private String profile;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "haircut_id")
    private Long haircutId;

    @Column(name = "username")
    private String username;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    public void setUpdatedAt() {
        updatedAt = LocalDateTime.now();
    }
}
