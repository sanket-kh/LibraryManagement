package com.librarymanagement.LibraryApplication.mappers;

import com.librarymanagement.LibraryApplication.entities.AccountType;

import java.util.List;

public class AccountTypeMapper {
    public static String mapToAccountTypeName(AccountType accountType){
       return  accountType.getAccountTypeName();

    }
    public static List<String> mapToListOfAccountTypeName(List<AccountType> accountTypeList){
        return accountTypeList.stream().map(AccountTypeMapper::mapToAccountTypeName).toList();
    }
}
