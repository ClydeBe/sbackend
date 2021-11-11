package com.thewheel.sawatu.database.repository;

import com.thewheel.sawatu.database.model.Role;
import com.thewheel.sawatu.database.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.email = :username OR u.username = :username")
    public Optional<User> findByUsernameOrEmail(String username);

    @Query("UPDATE User u SET u.role = :roleId WHERE u.username = :username")
    @Modifying
    public void saveUserRole(@Param("username") String username, @Param("roleId") Role role);

    @Query("UPDATE User u SET u.isActive = true WHERE u.username = :username")
    @Modifying
    public void activateAccount(@Param("username") String username);

}