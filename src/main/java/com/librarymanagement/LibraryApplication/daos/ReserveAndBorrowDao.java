package com.librarymanagement.LibraryApplication.daos;

import com.librarymanagement.LibraryApplication.entities.ReserveAndBorrow;
import com.librarymanagement.LibraryApplication.models.requests.TransactionSearchReq;

import java.util.List;

@Deprecated(since = "2024/02/22")
public interface ReserveAndBorrowDao {
    List<ReserveAndBorrow> searchFilter(TransactionSearchReq transactionSearchReq);
}
