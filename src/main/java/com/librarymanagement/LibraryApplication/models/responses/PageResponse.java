package com.librarymanagement.LibraryApplication.models.responses;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class PageResponse {
    private Long totalElements;
    private Integer totalPages;
    private Integer pageNo;
    private Integer pageSize;
    private Object content;
}
