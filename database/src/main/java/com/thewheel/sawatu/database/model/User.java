package com.thewheel.sawatu.database.model;

import lombok.*;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_user", uniqueConstraints = {
            @UniqueConstraint(name = "unique_email__constraint", columnNames = { "email" })
})
@EqualsAndHashCode(callSuper = false)
@Audited
@AuditTable(value = "audit_user")
public class User {

    @Id
    @Column(name = "username", nullable = false)
    private String username;

    @NotAudited
    @Column(name = "firstname")
    private String firstname;

    @NotAudited
    @Column(name = "lastname")
    private String lastname;

    @NotAudited
    @Column(name = "email", nullable = false)
    private  String email;

    @NotAudited
    @Column(name = "password", nullable = false)
    private String password;

    @NotAudited
    @Column(name = "photo")
    private String photo;

    @NotAudited
    @Column(name = "status")
    private String status;

    @Enumerated(EnumType.STRING)
    private Role role;

    @NotAudited
    @Column(name = "is_active", nullable = false, columnDefinition = "boolean default false")
    private Boolean isActive;

    @Embedded
    @NotAudited
    private Address address;

    @NotAudited
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    public void setUpdatedAt() {
        updatedAt = LocalDateTime.now();
    }

//    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
//    @JsonBackReference
//    private List<Appointment> asClientAppointments;
//
//    @OneToMany(mappedBy = "vendor", fetch = FetchType.LAZY)
//    @JsonBackReference
//    private List<Appointment> asVendorAppointments;
//
//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
//    @JsonBackReference
//    private List<Availability> asVendorAvailabilitis;
//
//    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
//    @JsonBackReference
//    private List<Comment> comments;
//
//    @OneToMany(mappedBy = "vendor", fetch = FetchType.LAZY)
//    @JsonBackReference
//    private List<Haircut> haircuts;
//
//    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
//    @JsonBackReference
//    private List<HaircutOrder> haircutOrders;
//
//    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
//    @JsonBackReference
//    private List<Post> posts;
//
//    @OneToMany(mappedBy = "vendor", fetch = FetchType.LAZY)
//    @JsonBackReference
//    private List<Product> products;
//
//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
//    private List<ProductOrder> productOrders;
//
//    @OneToMany(mappedBy = "reviewer", fetch = FetchType.LAZY)
//    private List<Reviews> toOtherReviews;
//
//    @OneToMany(mappedBy = "self", fetch = FetchType.LAZY)
//    private List<Reviews> fromOthersReviews;
//
//    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
//    private Statistics statistics;

}
