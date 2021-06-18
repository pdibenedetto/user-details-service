package com.appsdeveloperblog;

import com.appsdeveloperblog.userservice.data.UserEntity;
import com.appsdeveloperblog.userservice.data.UserEntityRepository;
import com.appsdeveloperblog.userservice.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Slf4j
@Component
public class InitialSetup {

    private final UserEntityRepository userEntityRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public InitialSetup(UserEntityRepository userEntityRepository,
                        BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userEntityRepository = userEntityRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @EventListener
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.debug(event.toString());
        userEntityRepository
                .findAll()
                .forEach(userEntity -> log.info("User found: " + userEntity.toString()));
    }
}
