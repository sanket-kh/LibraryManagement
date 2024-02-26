package com.librarymanagement.LibraryApplication.mappers;

import com.librarymanagement.LibraryApplication.entities.User;
import com.librarymanagement.LibraryApplication.models.dtos.ManageUserDto;
import com.librarymanagement.LibraryApplication.models.dtos.UserDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapperInterface {
    UserDto mapToUserDto(User user);
    List<ManageUserDto> mapToManageUserDtoList(List<User> user);

    User mapToUser(UserDto userDto);

    ManageUserDto mapToManageUserDto(User user);

}
