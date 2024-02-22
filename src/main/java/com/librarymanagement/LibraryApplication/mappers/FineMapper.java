package com.librarymanagement.LibraryApplication.mappers;


import com.library.fine.Dtos.FineCalculationRequest;
import com.library.fine.Dtos.FineDto;
import com.librarymanagement.LibraryApplication.entities.Fine;
import com.librarymanagement.LibraryApplication.models.dtos.FinesDto;
import com.librarymanagement.LibraryApplication.models.dtos.ReserveAndBorrowDto;
import com.librarymanagement.LibraryApplication.utils.Constants;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FineMapper {


    public static Fine mapToFine(FineDto fineDto) {
        Fine fine = new Fine();
        fine.setAmount(fineDto.getAmount());
        fine.setOverDue(fineDto.getOverDue());
        fine.setIsPaid(fineDto.getIsPaid());
        return fine;
    }

    public static FineCalculationRequest mapToFineCalculationRequest(ReserveAndBorrowDto reserveAndBorrow) {
        FineCalculationRequest fineCalculationRequest = new FineCalculationRequest();
        fineCalculationRequest.setReserved(reserveAndBorrow.getReserved());
        fineCalculationRequest.setReturnDate(reserveAndBorrow.getReturnDate());
        fineCalculationRequest.setIsIssued(reserveAndBorrow.getIsIssued());
        fineCalculationRequest.setFinePerDay(Constants.FINE_PER_DAY);
        fineCalculationRequest.setBorrowDurationLimit(Constants.MAX_BORROW_DURATION_PER_BOOK);
        fineCalculationRequest.setIssueDate(reserveAndBorrow.getIssueDate());
        fineCalculationRequest.setFine(null);
        return fineCalculationRequest;
    }
    public static FinesDto mapToFinesDto(Fine fine){
      FinesDto finesDto = new FinesDto();
      finesDto.setIsbn(fine.getReserveAndBorrow().getBook().getIsbn());
      finesDto.setTitle(fine.getReserveAndBorrow().getBook().getTitle());
      finesDto.setUsername(fine.getReserveAndBorrow().getUser().getUsername());
      finesDto.setFirstName(fine.getReserveAndBorrow().getUser().getFirstName());
      finesDto.setLastName(fine.getReserveAndBorrow().getUser().getLastName());
      finesDto.setAmount(fine.getAmount());
      finesDto.setIsPaid(fine.getIsPaid());
      finesDto.setOverDue(fine.getOverDue());
        return finesDto;
    }
    public static List<FinesDto> mapToFinesDto(List<Fine> fines){
        return fines.stream().map(FineMapper::mapToFinesDto).toList();
    }
}
