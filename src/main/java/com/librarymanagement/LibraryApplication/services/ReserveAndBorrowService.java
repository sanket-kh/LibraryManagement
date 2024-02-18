package com.librarymanagement.LibraryApplication.services;

import com.librarymanagement.LibraryApplication.models.requests.BorrowRequest;
import com.librarymanagement.LibraryApplication.models.requests.ReserveRequest;
import com.librarymanagement.LibraryApplication.models.requests.TransactionSearchReq;
import org.springframework.http.ResponseEntity;

public interface ReserveAndBorrowService {
    ResponseEntity<Object> burrowBook(BorrowRequest borrowRequest);

    ResponseEntity<Object> returnBook(BorrowRequest returnRequest);



    ResponseEntity<Object> viewBurrowedBooksByUser(String username);


    ResponseEntity<Object> getUserTransaction(String username, Integer pageSize, Integer pageNo);

    ResponseEntity<Object> getAllTransactions(Integer pageNo, Integer pageSize);

    ResponseEntity<Object> searchTransactions(TransactionSearchReq transactionSearchReq);
}
