package com.librarymanagement.LibraryApplication.daos;

import com.librarymanagement.LibraryApplication.entities.ReserveAndBorrow;
import com.librarymanagement.LibraryApplication.models.requests.TransactionSearchReq;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.List;

public class ReserveAndBorrowDaoImpl implements ReserveAndBorrowDao {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<ReserveAndBorrow> searchFilter(TransactionSearchReq transactionSearchReq) {


        String TRANSACTION_SEARCH_FILTER_QUERY = getQueryString(transactionSearchReq);


        System.out.println(" Query : " + TRANSACTION_SEARCH_FILTER_QUERY);
        Query searchQuery = this.entityManager.createQuery(TRANSACTION_SEARCH_FILTER_QUERY);

        if (transactionSearchReq.getIsbn() != null && transactionSearchReq.getIsbn() != 0) {
            searchQuery.setParameter("isbn", transactionSearchReq.getIsbn());
        }
        if (transactionSearchReq.getUsername() != null && !transactionSearchReq.getUsername().isEmpty()) {
            searchQuery.setParameter("username", "%" + transactionSearchReq.getUsername().trim() +
                                                 "%");
        }
        if (transactionSearchReq.getFromDate() != null && transactionSearchReq.getToDate() != null) {
            searchQuery.setParameter("fromDate", transactionSearchReq.getFromDate());
            searchQuery.setParameter("toDate", transactionSearchReq.getToDate());
        }
        if (transactionSearchReq.getFromDate() != null && transactionSearchReq.getToDate() == null) {
            searchQuery.setParameter("fromDate", transactionSearchReq.getFromDate());
        }

        var transactionList = searchQuery.getResultList();
        return transactionList;

    }

    private static String getQueryString(TransactionSearchReq transactionSearchReq) {
        String TRANSACTION_SEARCH_FILTER_QUERY = "SELECT R " +
                                                 "FROM ReserveAndBorrow R " +
                                                 "Inner Join Book B On R.book.id = B.id " +
                                                 "Inner Join User U On R.user.id = U.id " +
                                                 "WHERE 1=1 ";
        if (transactionSearchReq.getIsbn() != null && transactionSearchReq.getIsbn() != 0) {
            TRANSACTION_SEARCH_FILTER_QUERY = TRANSACTION_SEARCH_FILTER_QUERY +
                                              "AND B.isbn = :isbn ";
        }
        if (transactionSearchReq.getUsername() != null && !transactionSearchReq.getUsername().isEmpty()) {
            TRANSACTION_SEARCH_FILTER_QUERY = TRANSACTION_SEARCH_FILTER_QUERY +
                                              "AND U.username like :username ";
        }

        if (transactionSearchReq.getFromDate() != null && transactionSearchReq.getToDate() != null) {
                TRANSACTION_SEARCH_FILTER_QUERY = TRANSACTION_SEARCH_FILTER_QUERY +
                                                  "AND R.issueDate between :fromDate AND :toDate ";

        }
        if (transactionSearchReq.getToDate() == null){
            TRANSACTION_SEARCH_FILTER_QUERY = TRANSACTION_SEARCH_FILTER_QUERY +
                                              "AND R.issueDate = :fromDate ";
        }

        return TRANSACTION_SEARCH_FILTER_QUERY+"Order by R.issueDate";
    }

}

