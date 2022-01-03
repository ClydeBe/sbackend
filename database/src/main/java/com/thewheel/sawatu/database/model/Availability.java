package com.thewheel.sawatu.database.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Table(name = "availability")
@EntityListeners(AuditingEntityListener.class)
@Audited
@AuditTable(value = "audit_availability", schema = "audit")
public class Availability {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "availability_generator")
    @SequenceGenerator(name = "availability_generator", sequenceName = "s_availability", allocationSize = 1)
    private Long id;

    @Column(name = "availabilities", columnDefinition = "text")
    private String availabilities;

    @Column(name = "user_id", insertable = false, updatable = false)
    private String userName;

    @CreatedBy
    @ManyToOne(fetch = EAGER)
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
