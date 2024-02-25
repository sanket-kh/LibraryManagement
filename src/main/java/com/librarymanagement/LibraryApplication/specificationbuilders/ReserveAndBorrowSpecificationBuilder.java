package com.librarymanagement.LibraryApplication.specificationbuilders;

import com.librarymanagement.LibraryApplication.entities.ReserveAndBorrow;
import com.librarymanagement.LibraryApplication.models.requests.TransactionSearchReq;
import com.librarymanagement.LibraryApplication.specifications.ReserveAndBorrowSpecification;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReserveAndBorrowSpecificationBuilder {
    public static Specification<ReserveAndBorrow> buildSearchTransactionSpec(
            TransactionSearchReq transactionSearchReq) {
        Specification<ReserveAndBorrow> specification = Specification.where(null);
        if (transactionSearchReq.getUsername() != null && !transactionSearchReq.getUsername().trim()
                .isEmpty()) {
            specification = specification.and(ReserveAndBorrowSpecification.hasUsernameLike(
                    transactionSearchReq.getUsername()));
        }
        if (transactionSearchReq.getIsbn() != null && transactionSearchReq.getIsbn() != 0) {
            specification = specification.and(
                    ReserveAndBorrowSpecification.hasIsbn(transactionSearchReq.getIsbn()));
        }
        if (transactionSearchReq.getFromDate() != null && transactionSearchReq.getToDate() != null) {
            specification = specification.and(ReserveAndBorrowSpecification.hasIssuedDateBetween(
                    transactionSearchReq.getFromDate(), transactionSearchReq.getToDate()));
        }
        if (transactionSearchReq.getFromDate() != null && transactionSearchReq.getToDate() == null) {
            specification = specification.and(ReserveAndBorrowSpecification.hasIssuedDate(
                    transactionSearchReq.getFromDate()));
        }
        return specification;
    }
}
