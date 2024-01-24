package com.librarymanagement.LibraryApplication.repositories;


import com.librarymanagement.LibraryApplication.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

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
            " SET U.IS_NOT_LOCKED = TRUE , U.PASSWORD_ATTEMPT_COUNT = 0 , U.REMARK='' " +
            " Where U.USERNAME = :username ", nativeQuery = true)
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
            "SET U.IS_NOT_LOCKED = FALSE , U.REMARK = :remarks " +
            "WHERE U.USERNAME = :username", nativeQuery = true)
    Integer lockUserByUsername(String username, String remarks);


    @Query(value = """
            SELECT * FROM USER  WHERE USER.USERNAME LIKE :username
            """, nativeQuery = true)
    List<User> findUsersByUsernameLike(String username);

    @Query(value = """
            SELECT * FROM USER  WHERE USER.IS_NOT_LOCKED='FALSE'
            """, nativeQuery = true)
    List<User> findLockedUsers();

    @Query(value = """
            SELECT U from User U where U.username !=:username
            """)
    List<User> findAllUsersToManage(String username);
}
