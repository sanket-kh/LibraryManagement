package com.librarymanagement.LibraryApplication.mappers;

import com.librarymanagement.LibraryApplication.entities.Fine;
import com.librarymanagement.LibraryApplication.models.dtos.FinesDto;
import com.librarymanagement.LibraryApplication.models.responses.PageResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PageMapper {
    @Mapping(target = "totalPages", expression = "java(fines.getTotalPages())")
    @Mapping(target = "totalElements", expression = "java(fines.getTotalElements())")
    @Mapping(target = "pageSize", expression = "java(fines.getSize())")
    @Mapping(target = "pageNo", source = "pageNo")
    @Mapping(target = "content", source = "finesDto")
    PageResponse mapFinePageToPageResponse(Page<Fine> fines, List<FinesDto> finesDto,
                                           Integer pageNo);
}
