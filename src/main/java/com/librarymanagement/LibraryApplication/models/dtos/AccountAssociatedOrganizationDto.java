package com.librarymanagement.LibraryApplication.models.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AccountAssociatedOrganizationDto {
    private List<String> organizations;
}
