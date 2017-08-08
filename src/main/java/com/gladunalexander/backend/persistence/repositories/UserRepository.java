package com.gladunalexander.backend.persistence.repositories;

import com.gladunalexander.backend.persistence.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Alex on 06.07.2017.
 * Repository for users persisting
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findByEmail(String email);

    User findById(long id);


    @Modifying
    @Query("update User u set u.password = :password where u.id = :userId")
    void updateUserPassword(@Param("userId") long userId, @Param("password") String password);

    @Modifying
    @Query("update User u set u.enabled = :status where u.id = :id ")
    void updateUserStatus(@Param("id") long id, @Param("status") boolean status);
}
