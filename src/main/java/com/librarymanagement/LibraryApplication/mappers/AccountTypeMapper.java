package com.librarymanagement.LibraryApplication.mappers;

import com.librarymanagement.LibraryApplication.entities.AccountType;
import com.librarymanagement.LibraryApplication.models.dtos.AccountTypesDto;

import java.util.List;

public class AccountTypeMapper {
    public static String mapToAccountTypeName(AccountType accountType){
       return  accountType.getAccountTypeName();

    }
    public static AccountTypesDto mapToListOfAccountTypeName(List<AccountType> accountTypeList){
        List<String> listOfTypes = accountTypeList.stream()
                .map(AccountTypeMapper::mapToAccountTypeName).toList();
        return new AccountTypesDto(listOfTypes);
    }
}
