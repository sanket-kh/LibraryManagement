package com.librarymanagement.LibraryApplication.mappers;


import com.librarymanagement.LibraryApplication.entities.User;
import com.librarymanagement.LibraryApplication.enums.Role;
import com.librarymanagement.LibraryApplication.models.dtos.ManageUserDto;
import com.librarymanagement.LibraryApplication.models.dtos.UserDto;
import com.librarymanagement.LibraryApplication.models.requests.UserRegisterRequest;

import java.util.List;

public class UserMapper {
    public static UserDto mapUserToBaseUserDto(User user) {
        UserDto baseUserDto = new UserDto();
        baseUserDto.setAddress(user.getAddress());
        baseUserDto.setEmail(user.getEmail());
        baseUserDto.setPhone(user.getPhone());
        baseUserDto.setFirstName(user.getFirstName());
        baseUserDto.setLastName(user.getLastName());
        baseUserDto.setUsername(user.getUsername());
        return baseUserDto;
    }

    public static User mapToUser(UserRegisterRequest userRegisterRequest, String encodedPassword) {

        User user = new User();

        user.setAddress(userRegisterRequest.getAddress());
        user.setEmail(userRegisterRequest.getEmail());
        user.setPassword(encodedPassword);
        user.setPhone(userRegisterRequest.getPhone());
        user.setFirstName(userRegisterRequest.getFirstName());
        user.setLastName(userRegisterRequest.getLastName());
        user.setUsername(userRegisterRequest.getUsername());
        user.setStatus(Boolean.TRUE);
        user.setIsNotLocked(Boolean.TRUE);
        user.setPasswordAttemptCount(0);
        return user;
    }
    public static User mapToAdmin(UserRegisterRequest userRegisterRequest, String encodedPassword) {

        User user = new User();

        user.setAddress(userRegisterRequest.getAddress());
        user.setEmail(userRegisterRequest.getEmail());
        user.setPassword(encodedPassword);
        user.setPhone(userRegisterRequest.getPhone());
        user.setFirstName(userRegisterRequest.getFirstName());
        user.setLastName(userRegisterRequest.getLastName());
        user.setUsername(userRegisterRequest.getUsername());
        user.setStatus(Boolean.TRUE);
        user.setIsNotLocked(Boolean.TRUE);
        user.setPasswordAttemptCount(0);
        user.setRole(Role.LIBRARIAN);
        return user;
    }

    public static ManageUserDto mapToManageUserDto(User user) {
        ManageUserDto manageUserDto = new ManageUserDto();
        manageUserDto.setIsNotLocked(user.getIsNotLocked());
        manageUserDto.setAddress(user.getAddress());
        manageUserDto.setEmail(user.getEmail());
        manageUserDto.setPhone(user.getPhone());
        manageUserDto.setFirstName(user.getFirstName());
        manageUserDto.setLastName(user.getLastName());
        manageUserDto.setUsername(user.getUsername());
        manageUserDto.setStatus(user.getStatus());
        manageUserDto.setRemark(user.getRemark());
        manageUserDto.setRole(user.getRole().name());
        return manageUserDto;
    }

    public static List<ManageUserDto> mapToManageUserDto(List<User> users) {
       return users.stream().map(UserMapper::mapToManageUserDto).toList();
    }
}
