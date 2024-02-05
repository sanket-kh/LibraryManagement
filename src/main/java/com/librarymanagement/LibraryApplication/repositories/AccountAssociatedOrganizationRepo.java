package com.librarymanagement.LibraryApplication.repositories;

import com.librarymanagement.LibraryApplication.entities.AccountAssociatedOrganization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountAssociatedOrganizationRepo extends JpaRepository<AccountAssociatedOrganization, Long> {
    @Query(value = """
                SELECT ORGANIZATION FROM ACCOUNT_ASSOCIATED_ORGANIZATION
                INNER JOIN LIBRARY_MANAGEMENT_SYSTEM.ACCOUNT_TYPE A ON ACCOUNT_ASSOCIATED_ORGANIZATION.ACCOUNT_TYPE_ID = A.ID
                WHERE A.ACCOUNT_TYPE=:accountTypeName
                AND ACCOUNT_ASSOCIATED_ORGANIZATION.ACTIVE='Y'
            """, nativeQuery = true)
    List<String> findListOfOrganizationNameByAccountTypeName(String accountTypeName);

    AccountAssociatedOrganization findAccountAssociatedOrganizationByOrganizationNameAndActive(String organizationName, Character active);
}
