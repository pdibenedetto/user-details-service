package com.appsdeveloperblog.userservice.services;

import com.appsdeveloperblog.userservice.model.User;

public interface UsersService {
    User getUserDetails(String userName);
    User getUserDetails(String userName, String password);
}
