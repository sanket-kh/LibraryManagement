package com.librarymanagement.LibraryApplication.services.serviceImpls;

import com.librarymanagement.LibraryApplication.entities.ReserveAndBorrow;
import com.librarymanagement.LibraryApplication.repositories.ReserveAndBurrowRepo;
import com.librarymanagement.LibraryApplication.services.AccountLockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Log4j2
@Service
@EnableScheduling
@RequiredArgsConstructor
public class AccountLockServiceImpl implements AccountLockService {
    private final ReserveAndBurrowRepo reserveAndBurrowRepo;
    @Override
    public void lockAccountDueToExceedingFine() {

    }

    @Override
    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.DAYS )
    public void lockAccountDueToDelayedReturn() {
        LocalDate currentDate = LocalDate.now();
        List<ReserveAndBorrow> reserveAndBorrowList =
                reserveAndBurrowRepo.reserveAndBorrowListOfDelayedReturn(currentDate);
        reserveAndBorrowList.forEach(
                reserveAndBorrow -> {
                    reserveAndBorrow.getUser().setIsNotLocked(Boolean.FALSE);
                    reserveAndBurrowRepo.save(reserveAndBorrow);
                });
    }
}
