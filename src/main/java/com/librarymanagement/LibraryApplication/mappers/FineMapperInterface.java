package com.librarymanagement.LibraryApplication.mappers;

import com.library.fine.Dtos.FineCalculationRequest;
import com.library.fine.Dtos.FineDto;
import com.library.fine.Dtos.FinePaymentRequest;
import com.librarymanagement.LibraryApplication.entities.Fine;
import com.librarymanagement.LibraryApplication.models.dtos.FinesDto;
import com.librarymanagement.LibraryApplication.models.dtos.ReserveAndBorrowDto;
import com.librarymanagement.LibraryApplication.models.requests.PaymentRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FineMapperInterface {
    Fine mapToFine(FineDto fineDto);

    @Mapping(target = "borrowDurationLimit", expression = "java(com.librarymanagement" +
                                                          ".LibraryApplication.utils.Constants" + ".MAX_BORROW_DURATION_PER_BOOK)")
    @Mapping(target = "finePerDay", expression =
            "java(com.librarymanagement.LibraryApplication" + ".utils.Constants.FINE_PER_DAY)")
    FineCalculationRequest mapToFineCalculationRequest(ReserveAndBorrowDto reserveAndBorrow);

    @Mapping(target = "isbn", source = "fine.reserveAndBorrow.book.isbn")
    @Mapping(target = "title", source = "fine.reserveAndBorrow.book.title")
    @Mapping(target = "firstName", source = "fine.reserveAndBorrow.user.firstName")
    @Mapping(target = "lastName", source = "fine.reserveAndBorrow.user.lastName")
    @Mapping(target = "username", source = "fine.reserveAndBorrow.user.username")
    FinesDto mapToFinesDto(Fine fine);

    List<FinesDto> mapToFinesDto(List<Fine> fines);

    @Mapping(target = "isPaid", expression = "java(Boolean.FALSE)")
    @Mapping(target = "paidAmount", source = "paymentRequest.amount")
    @Mapping(target = "amount", source = "owedAmount")
    @Mapping(target = "overDue", expression = "java(owedAmount/com.librarymanagement" +
                                              ".LibraryApplication.utils.Constants.FINE_PER_DAY)")
    FinePaymentRequest mapToFinPaymentRequest(Integer owedAmount, PaymentRequest paymentRequest);
}
