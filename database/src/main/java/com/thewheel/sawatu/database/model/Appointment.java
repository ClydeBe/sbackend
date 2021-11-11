package com.thewheel.sawatu.database.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
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
@Table(name = "t_appointment",
    indexes = {
        @Index(name = "client_appointment__idx", columnList = "client_id"),
        @Index(name = "vendor_appointment__idx", columnList = "vendor_id")
    }
)
@EntityListeners(AuditingEntityListener.class)
@Audited
@AuditTable(value = "audit_appointment")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    // If false, the address is the client's one!
    @Column(name = "is_vendor_address")
    @NotAudited
    private boolean isVendorAddress;

    @Column(name = "client_id", insertable = false, updatable = false)
    private String clientName;

    @Column(name = "vendor_id", insertable = false, updatable = false)
    private String vendorName;

    @ManyToOne
    @JoinColumn(name = "haircut_order_id", foreignKey = @ForeignKey(name = "appointment_haircut_order__fk"))
    private HaircutOrder haircutOrder;

    @NotAudited
    @CreatedBy
    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "client_id", foreignKey = @ForeignKey(name = "appointment_client__fk"))
    private User client;

    @NotAudited
    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "vendor_id", foreignKey = @ForeignKey(name = "appointment_vendor__fk"))
    private User vendor;

    @OneToOne
    @JoinColumn(name = "haircut_id", foreignKey = @ForeignKey(name = "appointment_haircut__fk"))
    private Haircut haircut;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    public void setUpdatedAt() {
        updatedAt = LocalDateTime.now();
    }
}
