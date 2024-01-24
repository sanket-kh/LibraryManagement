package com.librarymanagement.LibraryApplication.mappers;

import com.librarymanagement.LibraryApplication.entities.Fine;
import com.librarymanagement.LibraryApplication.models.responses.PageResponse;
import org.springframework.data.domain.Page;

public class PageMapper {
    public static PageResponse mapFinePageToPageResponse(Page<Fine> page){
        PageResponse pageResponse =new PageResponse();
        pageResponse.setPageSize(page.getSize());
        pageResponse.setPageNo(page.getPageable().getPageNumber());
        pageResponse.setPageSize(page.getPageable().getPageSize());
        pageResponse.setTotalPages(page.getTotalPages());
        pageResponse.setContent(FineMapper.mapToFinesDto(page.getContent()));
        pageResponse.setTotalElements(page.getTotalElements());
        return pageResponse;
    }
}
