package com.appsdeveloperblog.userservice.services;

import com.appsdeveloperblog.userservice.data.UserEntity;
import com.appsdeveloperblog.userservice.data.UserEntityRepository;
import com.appsdeveloperblog.userservice.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
public class UsersServiceImpl implements UsersService {

    private final UserEntityRepository userEntityRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UsersServiceImpl(UserEntityRepository userEntityRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userEntityRepository = userEntityRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User getUserDetails(String userName) {
        User user = new User();
        Optional<UserEntity> userEntityOptional = userEntityRepository.findByEmail(userName);

        userEntityOptional.ifPresent(userEntity -> {
            BeanUtils.copyProperties(userEntityOptional.get(), user);
        });

        return user;

    }

    @Override
    public User getUserDetails(String userName, String password) {
        AtomicReference<User> user = new AtomicReference<>();

        Optional<UserEntity> userEntityOptional = userEntityRepository.findByEmail(userName);
        userEntityOptional.ifPresent(userEntity -> {
            if (bCryptPasswordEncoder.matches(password, userEntityOptional.get().getEncryptedPassword())) {
                log.info("Passwords match!");
                user.set(new User());
                BeanUtils.copyProperties(userEntityOptional.get(), user.get());
            }
        });
        return user.get();
    }
}
