package com.appsdeveloperblog.userservice.controllers;

import com.appsdeveloperblog.userservice.model.User;
import com.appsdeveloperblog.userservice.model.VerifyPassword;
import com.appsdeveloperblog.userservice.services.UsersService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {
    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/{userName}")
    public User getUser(@PathVariable("userName") String userName) {
        return usersService.getUserDetails(userName);
    }

    @PostMapping("/{userName}/verify-password")
    public VerifyPassword verifyUserPassword(@PathVariable("userName") String userName,
                                             @RequestBody String password) {
        VerifyPassword verifyPassword = new VerifyPassword(false);
        User user = usersService.getUserDetails(userName, password);
        if(user != null) {
            verifyPassword.setResult(true);
        }

        return verifyPassword;
    }
}
