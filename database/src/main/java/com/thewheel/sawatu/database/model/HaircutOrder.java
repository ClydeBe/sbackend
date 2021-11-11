package com.thewheel.sawatu.database.model;

import lombok.*;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.FetchType.EAGER;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_haircut_order",
    indexes = {
        @Index(name = "client_haircut_order_id__idx", columnList = "client_id")
    }
)
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
@Audited
@AuditTable(value = "audit_haircut_order")
public class HaircutOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description", columnDefinition = "text", nullable = false)
    private String description;

    @Column(name = "price")
    private Float price;

    @Column(name = "haircut_order_date")
    private LocalDateTime date;

    @Column(name = "client_id", insertable = false, updatable = false)
    private String clientName;

    @CreatedBy
    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "client_id", foreignKey = @ForeignKey(name = "haircut_order_client__fk"))
    private User client;

    @ManyToOne
    @JoinColumn(name = "haircut_id", foreignKey = @ForeignKey(name = "haircut_order_haircut__fk"))
    private Haircut haircut;

    @Singular
    @OneToMany(mappedBy = "haircutOrder")
    private List<Appointment> appointments;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    public void setUpdatedAt() {
        updatedAt = LocalDateTime.now();
    }
}
