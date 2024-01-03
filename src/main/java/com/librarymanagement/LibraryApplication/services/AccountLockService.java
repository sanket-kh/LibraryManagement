package com.librarymanagement.LibraryApplication.services;

public interface AccountLockService {

    void lockAccountDueToExceedingFine();
    void lockAccountDueToDelayedReturn();
}
