package com.librarymanagement.LibraryApplication.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AccountTypesDto {
    private List<String> accountTypeNames;

}
