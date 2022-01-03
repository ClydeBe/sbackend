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
@Table(name = "product_order")
@EntityListeners(AuditingEntityListener.class)
@Audited
@AuditTable(value = "audit_product_order", schema = "audit")
public class ProductOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_order_generator")
    @SequenceGenerator(name = "product_order_generator", sequenceName = "s_product_order", allocationSize = 1)
    private Long id;

    @Column(name = "items", columnDefinition = "text", nullable = false)
    private String items;

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
