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
@Table(name = "t_product_order",
    indexes = {
        @Index(name = "user_product_order__idx", columnList = "user_id")
    }
)
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
@Audited
@AuditTable(value = "audit_product_order")
public class ProductOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "items", columnDefinition = "text", nullable = false)
    private String items;

    @Column(name = "user_id", insertable = false, updatable = false)
    private String userName;

    @CreatedBy
    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "product_order_user__fk"))
    private User user;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    public void setUpdatedAt() {
        updatedAt = LocalDateTime.now();
    }
}
