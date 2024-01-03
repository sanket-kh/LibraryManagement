package com.librarymanagement.LibraryApplication.repositories;


import com.librarymanagement.LibraryApplication.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    User findUserByUsername(String username);

    @Query(value = "SELECT * " +
            "FROM USER U " +
            "WHERE U.username =:username " +
            "AND U.PASSWORD_ATTEMPT_COUNT < 5 " +
            "AND u.IS_NOT_LOCKED IS TRUE " +
            "AND u.STATUS IS TRUE", nativeQuery = true)
    User loadUserByUsername(String username);

    @Modifying
    @Transactional
    @Query(value = "UPDATE USER U  " +
            " SET U.IS_NOT_LOCKED = TRUE , U.PASSWORD_ATTEMPT_COUNT = 0 " +
            "WHERE U.USERNAME = :username ", nativeQuery = true)
    Integer unlockUser(String username);

    @Modifying
    @Transactional
    @Query(value = "UPDATE USER U " +
            "SET U.PASSWORD_ATTEMPT_COUNT = 0 " +
            "WHERE U.USERNAME =:username ", nativeQuery = true)
    Integer refreshLoginAttempts(String username);

    @Modifying
    @Transactional
    @Query(value = "UPDATE USER U " +
            "SET U.IS_NOT_LOCKED = FALSE , U.REMARKS = :remarks " +
            "WHERE U.USERNAME = :username", nativeQuery = true)
    Integer lockUserByUsername(String username, String remarks);
}
