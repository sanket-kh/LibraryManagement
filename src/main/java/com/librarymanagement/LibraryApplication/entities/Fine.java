package com.librarymanagement.LibraryApplication.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "FINE")
public class Fine {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "OVER_DUE")
    private Integer overDue;

    @Column(name = "AMOUNT")
    private Integer amount;

    @Column(name = "IS_PAID")
    private Boolean isPaid;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RESERVE_AND_BORROW_ID")
    private ReserveAndBorrow reserveAndBorrow;


}
