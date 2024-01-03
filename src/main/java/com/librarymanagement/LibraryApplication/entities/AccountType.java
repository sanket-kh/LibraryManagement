package com.librarymanagement.LibraryApplication.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "ACCOUNT_TYPE")
public class AccountType {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ACCOUNT_TYPE", unique = true)
    private String accountTypeName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accountType")
    private List<AccountAssociatedOrganization> accountAssociatedOrganizations;
}
