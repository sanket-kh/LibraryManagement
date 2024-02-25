package com.librarymanagement.LibraryApplication.specifications;

import com.librarymanagement.LibraryApplication.entities.ReserveAndBorrow;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReserveAndBorrowSpecification {

    public static Specification<ReserveAndBorrow> hasIsbn(Long isbn) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(
                root.get("book").get("isbn"), isbn));
    }

    public static Specification<ReserveAndBorrow> hasUsernameLike(String username) {
        return (root, query, criteriaBuilder) -> {
            String keyword = "%" + username.trim() + "%";
            return criteriaBuilder.like(root.get("user").get("username"), keyword);
        };
    }

    public static Specification<ReserveAndBorrow> hasIssuedDate(LocalDateTime fromDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("issueDate"),
                fromDate);
    }

    public static Specification<ReserveAndBorrow> hasIssuedDateBetween(LocalDateTime fromDate,
                                                                       LocalDateTime toDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("issueDate"),
                fromDate, toDate);
    }

}
