package com.librarymanagement.LibraryApplication.models.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExistingBookRequest {
    @Min(value = 3L , message="ISBN should be min 3 digits")
    @Max(value =13L , message="ISBN should be max 13 digits")
    private Long isbn;

    @Min(value = 1L , message="ISBN should be min 1 digits")
    @Max(value =2L , message="ISBN should be max 2 digits")
    private Integer copies;
}
