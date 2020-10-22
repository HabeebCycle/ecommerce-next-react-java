package com.habeebcycle.marketplace.controller.mapper;

import com.habeebcycle.marketplace.model.entity.User;
import com.habeebcycle.marketplace.payload.user.UserUpdateRequest;

public class UserRequestMapper {

    public static User mapUser(User user, UserUpdateRequest userUpdateRequest){
        user.setEmail(userUpdateRequest.getEmail());
        user.setPassword(userUpdateRequest.getPassword());
        user.setName(userUpdateRequest.getName());
        user.setUserDetails(userUpdateRequest.getUserDetails());

        return user;
    }
}
