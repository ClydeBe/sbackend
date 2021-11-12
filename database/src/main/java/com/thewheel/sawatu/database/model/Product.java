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
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "t_product",
    indexes = {
        @Index(name = "product_price__idx", columnList = "price")
    }
)
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
@Audited
@AuditTable(value = "audit_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "label")
    private String label;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "image")
    private String image;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "price")
    private Float price;

    @Column(name = "usage")
    private  String usage;

    @Column(name = "characteristics", columnDefinition = "text")
    private String characteristics;

    @Column(name = "vendor_id", insertable = false, updatable = false)
    private String vendorName;

    @CreatedBy
    @ManyToOne(fetch = EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "vendor_id")
    private User vendor;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    public void setUpdatedAt() {
        updatedAt = LocalDateTime.now();
    }
}
