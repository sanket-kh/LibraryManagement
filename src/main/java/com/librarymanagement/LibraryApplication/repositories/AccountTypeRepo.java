package com.librarymanagement.LibraryApplication.repositories;

import com.librarymanagement.LibraryApplication.entities.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountTypeRepo extends JpaRepository<AccountType, Long> {
    @Query(value = """
                 SELECT * FROM ACCOUNT_TYPE
            """, nativeQuery = true)
    List<AccountType> findAllAccountTypes();
    AccountType findAccountTypeByAccountTypeNameIgnoreCase(String accountTypeName);
    AccountType findAccountTypeByAccountTypeName(String accountTypeName);
    @Query(value = """
            SELECT A.ACCOUNT_TYPE FROM ACCOUNT_TYPE A
            """, nativeQuery = true)
    List<String> findAccountTypeNameList();
}
