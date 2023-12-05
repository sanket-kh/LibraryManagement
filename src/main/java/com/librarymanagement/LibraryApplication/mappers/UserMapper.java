package com.librarymanagement.LibraryApplication.mappers;


import com.librarymanagement.LibraryApplication.entities.User;
import com.librarymanagement.LibraryApplication.models.dtos.userdtos.UserDto;
import com.librarymanagement.LibraryApplication.models.requests.UserRegisterRequest;

public class UserMapper {
    public static UserDto mapUserToBaseUserDto(User user){
        UserDto baseUserDto = new UserDto();
        baseUserDto.setAddress(user.getAddress());
        baseUserDto.setEmail(user.getEmail());
        baseUserDto.setPhone(user.getPhone());
        baseUserDto.setStatus(user.getStatus());
        baseUserDto.setFirstName(user.getFirstName());
        baseUserDto.setLastName(user.getLastName());
        baseUserDto.setUsername(user.getUsername());
        return baseUserDto;
    }

    public static User mapToUser(UserRegisterRequest userRegisterRequest) {

        User user = new User();

        user.setAddress(userRegisterRequest.getAddress());
        user.setEmail(userRegisterRequest.getEmail());
        user.setPassword(userRegisterRequest.getPassword());
        user.setPhone(userRegisterRequest.getPhone());
        user.setFirstName(userRegisterRequest.getFirstName());
        user.setLastName(userRegisterRequest.getLastName());
        user.setUsername(userRegisterRequest.getUsername());
        return user;
    }
}
