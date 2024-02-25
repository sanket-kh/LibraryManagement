package com.librarymanagement.LibraryApplication.specificationbuilders;

import com.librarymanagement.LibraryApplication.entities.Book;
import com.librarymanagement.LibraryApplication.models.requests.BookSearchFilterRequest;
import com.librarymanagement.LibraryApplication.specifications.BookSpecification;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecificationBuilder {
    public static Specification<Book> searchBookSpecBuilder(BookSearchFilterRequest bookSearchFilterRequest){
        Specification<Book> specification = Specification.where(null);
        if (bookSearchFilterRequest.getTitle() != null && !bookSearchFilterRequest.getTitle()
                .trim().isEmpty()) {
            specification = specification.and(
                    BookSpecification.hasTitleLike(bookSearchFilterRequest.getTitle()));
        }
        if (bookSearchFilterRequest.getAuthor() != null && !bookSearchFilterRequest.getAuthor()
                .trim().isEmpty()) {
            specification = specification.and(
                    BookSpecification.hasAuthorLike(bookSearchFilterRequest.getAuthor()));
        }
        if (bookSearchFilterRequest.getIsbn() != null && bookSearchFilterRequest.getIsbn() != 0) {
            specification = specification.and(
                    BookSpecification.hasIsbnEquals(bookSearchFilterRequest.getIsbn()));
        }
        return specification.and(BookSpecification.isAvailable());
    }
}
