package com.librarymanagement.LibraryApplication.mappers;


import com.library.fine.Dtos.FineCalculationRequest;
import com.library.fine.Dtos.FineDto;
import com.librarymanagement.LibraryApplication.entities.Fine;
import com.librarymanagement.LibraryApplication.entities.ReserveAndBorrow;
import com.librarymanagement.LibraryApplication.models.dtos.reserveandburrowdtos.ReserveAndBorrowDto;
import com.librarymanagement.LibraryApplication.utils.Constants;

public class FineMapper {

    public static FineDto mapToFineDto(Fine fine){
        FineDto fineDto = new FineDto();
        fineDto.setAmount(fine.getAmount());
        fineDto.setOverDue(fine.getOverDue());
        fineDto.setIsPaid(fine.getIsPaid());
        return fineDto;
    }
    public static Fine mapToFine(FineDto fineDto){
        Fine fine = new Fine();
        fine.setAmount(fineDto.getAmount());
        fine.setOverDue(fineDto.getOverDue());
        fine.setIsPaid(fineDto.getIsPaid());
        return fine;
    }

    public static FineCalculationRequest mapToFineCalculationRequest(ReserveAndBorrowDto reserveAndBorrow){
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
}
