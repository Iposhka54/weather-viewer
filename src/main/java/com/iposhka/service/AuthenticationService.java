package com.iposhka.service;

import com.iposhka.dto.CreateUserDto;
import com.iposhka.model.User;
import com.iposhka.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    public AuthenticationService(UserRepository userRepository){

        this.userRepository = userRepository;
    }

    public void signIn(CreateUserDto userDto){
        //need mapper and password service with encrypt method
        User user = User.builder()
                .login(userDto.getUsername())
                .password(userDto.getPassword())
                .build();

        userRepository.save(user);
    }
}
