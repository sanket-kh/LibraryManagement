package com.librarymanagement.LibraryApplication.specifications;

import com.librarymanagement.LibraryApplication.entities.Book;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookSpecification {
    public static Specification<Book> hasTitleLike(String title) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"),
                "%" + title + "%"));
    }

    public static Specification<Book> hasAuthorLike(String author) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("author"),
                "%" + author + "%"));
    }

    public static Specification<Book> hasIsbnEquals(Long isbn) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isbn"), isbn));
    }

    public static Specification<Book> isAvailable() {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.isTrue(root.get("isAvailable")));
    }

}
