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
@Table(name = "product")
@EntityListeners(AuditingEntityListener.class)
@Audited
@AuditTable(value = "audit_product", schema = "audit")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_generator")
    @SequenceGenerator(name = "product_generator", sequenceName = "s_product", allocationSize = 1)
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
