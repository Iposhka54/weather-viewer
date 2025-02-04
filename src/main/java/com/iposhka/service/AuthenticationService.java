package com.iposhka.service;

import com.iposhka.dto.CreateUserDto;
import com.iposhka.mapper.UserMapper;
import com.iposhka.model.User;
import com.iposhka.repository.UserRepository;
import com.iposhka.util.CryptUtil;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public AuthenticationService(UserRepository userRepository, UserMapper userMapper){

        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public void signIn(CreateUserDto userDto){
        User entity = userMapper.toEntity(userDto);
        String cryptPassword = CryptUtil.crypt(entity.getPassword());
        entity.setPassword(cryptPassword);
        userRepository.save(entity);
    }
}
