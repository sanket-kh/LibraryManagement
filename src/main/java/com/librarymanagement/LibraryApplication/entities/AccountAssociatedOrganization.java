package com.librarymanagement.LibraryApplication.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "ACCOUNT_ASSOCIATED_ORGANIZATION")
public class AccountAssociatedOrganization {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ORGANIZATION")
    private String organizationName;

    @Column(name= "ACTIVE")
    private Character active;

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_TYPE_ID")
    private AccountType accountType;

}
