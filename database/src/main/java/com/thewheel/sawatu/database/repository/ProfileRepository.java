package com.thewheel.sawatu.database.repository;

import com.thewheel.sawatu.database.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileRepository, String> {

    @Query("SELECT p FROM Profile p WHERE p.userName = :username")
    public Optional<Profile> findByUsername(String username);

}
