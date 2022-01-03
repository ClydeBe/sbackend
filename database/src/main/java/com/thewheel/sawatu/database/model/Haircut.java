package com.thewheel.sawatu.database.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.EAGER;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "haircut")
@Audited
@AuditTable(value = "audit_haircut", schema = "audit")
public class Haircut {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "haircut_generator")
    @SequenceGenerator(name = "haircut_generator", sequenceName = "s_haircut", allocationSize = 1)
    private Long id;

    @Column(name = "label")
    private String label;

    // Duration in minutes
    @Column(name = "duration")
    private Short duration;

    @Column(name = "price")
    private Float price;

    @Column(name = "vat_ratio")
    private Float vatRatio;

    @Column(name = "photo", columnDefinition = "text", nullable = false)
    private String photo;

    @Column(name = "vendor_id", insertable = false, updatable = false)
    private String vendorName;

    @ManyToOne(fetch = EAGER)
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
