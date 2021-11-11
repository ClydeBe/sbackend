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
@Table(name = "t_avaibility",
    indexes = {
        @Index(name = "user_availability__idx", columnList = "user_id")
    }
)
@EntityListeners(AuditingEntityListener.class)
@Audited
@AuditTable(value = "audit_availability")
public class Availability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "availabilities", columnDefinition = "text")
    private String availabilities;

    @Column(name = "user_id", insertable = false, updatable = false)
    private String userName;

    @CreatedBy
    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "availability_user__fk"))
    private User user;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    public void setUpdatedAt() {
        updatedAt = LocalDateTime.now();
    }
}
