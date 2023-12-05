package com.librarymanagement.LibraryApplication.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "USER")
public class User {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "PHONE")
    private Long phone;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "STATUS")
    private Boolean status;

    @OneToMany(mappedBy = "user")
    private List<ReserveAndBorrow> reserveAndBorrowList;

}
