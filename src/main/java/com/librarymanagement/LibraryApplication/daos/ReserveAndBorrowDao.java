package com.librarymanagement.LibraryApplication.daos;

import com.librarymanagement.LibraryApplication.entities.ReserveAndBorrow;
import com.librarymanagement.LibraryApplication.models.requests.TransactionSearchReq;

import java.util.List;

public interface ReserveAndBorrowDao {
    List<ReserveAndBorrow> searchFilter(TransactionSearchReq transactionSearchReq);
}
