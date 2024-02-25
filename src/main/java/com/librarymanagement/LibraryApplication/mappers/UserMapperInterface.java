package com.librarymanagement.LibraryApplication.mappers;

import com.librarymanagement.LibraryApplication.entities.User;
import com.librarymanagement.LibraryApplication.models.dtos.ManageUserDto;
import com.librarymanagement.LibraryApplication.models.dtos.UserDto;
import com.librarymanagement.LibraryApplication.models.requests.ChangeUserDetailsReq;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface UserMapperInterface {
    UserDto mapToUserDto(User user);

    User mapToUser(UserDto user);

    ManageUserDto mapToManageUserDto(User user);

    default User mapToUser(ChangeUserDetailsReq changeUserDetailsReq, User user) {
        user.setAddress(changeUserDetailsReq.getAddress());
        user.setEmail(changeUserDetailsReq.getEmail());
        user.setFirstName(changeUserDetailsReq.getFirstName());
        user.setLastName(changeUserDetailsReq.getLastName());
        user.setPhone(changeUserDetailsReq.getPhone());
        return user;
    }
    default List<ManageUserDto> mapToManageUserDto(List<User> users) {
        return users.stream().map(UserMapper::mapToManageUserDto).toList();
    }
}
