package com.thewheel.sawatu.database.model;

import com.thewheel.sawatu.Role;
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
@Table(name = "user")
@Audited
@AuditTable(value = "audit_user", schema = "audit")
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

}
