package com.librarymanagement.LibraryApplication.mappers;

import com.librarymanagement.LibraryApplication.entities.User;
import com.librarymanagement.LibraryApplication.models.dtos.ManageUserDto;
import com.librarymanagement.LibraryApplication.models.dtos.UserDto;
import com.librarymanagement.LibraryApplication.models.requests.ChangeUserDetailsReq;
import com.librarymanagement.LibraryApplication.models.requests.UserRegisterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapperInterface {
    UserDto mapToUserDto(User user);
    List<ManageUserDto> mapToManageUserDtoList(List<User> user);

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "reserveAndBorrowList", ignore = true)
    @Mapping(target = "remark", ignore = true)
    @Mapping(target = "passwordAttemptCount", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "isNotLocked", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "accountList", ignore = true)
    User mapToUser(UserDto userDto);

    ManageUserDto mapToManageUserDto(User user);
    @Mapping(target = "status", expression = "java(true)")
    @Mapping(target = "isNotLocked", expression ="java(true)" )
    @Mapping(target = "passwordAttemptCount", expression = "java(0)")
    @Mapping(target = "password", source = "encodedPassword")
    @Mapping(target = "accountList", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "remark", ignore = true)
    @Mapping(target = "reserveAndBorrowList", ignore = true)
    @Mapping(target = "role", ignore = true)
    User mapToUser(UserRegisterRequest userRegisterRequest, String encodedPassword);

    @Mapping(target = "username", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "reserveAndBorrowList", ignore = true)
    @Mapping(target = "remark", ignore = true)
    @Mapping(target = "passwordAttemptCount", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "isNotLocked", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "accountList", ignore = true)
    User updateUserFromRequest(ChangeUserDetailsReq changeUserDetailsReq, @MappingTarget User user);

}
