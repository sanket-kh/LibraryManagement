package com.librarymanagement.LibraryApplication.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "LIBRARIAN_ACCOUNT_DETAILS")
public class LibrarianAccount {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ACCOUNT_NAME")
    private String accountName;

    @Column(name = "ACCOUNT_NUMBER")
    private Long accountNumber;

    @Column(name = "RECORDED_DATE")
    private LocalDate recordedDate;

    @Column(name="MODIFIED_DATE")
    private LocalDate modifiedDate;

    @Column(name = "ACTIVE")
    private Character active;

    @OneToOne
    private AccountType accountType;

    @OneToOne
    private AccountAssociatedOrganization accountAssociatedOrganization;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User librarian;



}
