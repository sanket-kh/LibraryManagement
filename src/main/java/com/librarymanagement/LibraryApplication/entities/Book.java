package com.librarymanagement.LibraryApplication.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "BOOK")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;

    @Column(name = "ISBN", nullable = false, unique = true)
    private Long isbn;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "AUTHOR", nullable = false)
    private String author;

    @Column(name = "COPIES", nullable = false)
    private Integer copies;

    @Column(name = "IS_AVAILABLE")
    private Boolean isAvailable;

    @OneToMany(mappedBy = "book")
    private List<ReserveAndBorrow> reserveAndBorrowList;

}
