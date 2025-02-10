package com.iposhka.service;

import com.iposhka.dto.CreateUserDto;
import com.iposhka.dto.SessionDto;
import com.iposhka.dto.UserLoginDto;
import com.iposhka.exception.DatabaseException;
import com.iposhka.exception.InvalidCredentialsException;
import com.iposhka.exception.UserAlreadyExistException;
import com.iposhka.exception.UserNotFoundException;
import com.iposhka.mapper.UserMapper;
import com.iposhka.model.User;
import com.iposhka.repository.UserRepository;
import com.iposhka.util.CryptUtil;
import jakarta.transaction.Transactional;
import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final SessionService sessionService;

    public AuthenticationService(UserRepository userRepository, UserMapper userMapper, SessionService sessionService){

        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.sessionService = sessionService;
    }

    @Transactional
    public void signUp(CreateUserDto userDto){
        if(!userDto.getPassword().equals(userDto.getRepeatedPassword())){
            throw new InvalidCredentialsException("Passwords don't match");
        }

        User entity = userMapper.toEntity(userDto);
        String cryptPassword = CryptUtil.crypt(entity.getPassword());
        entity.setPassword(cryptPassword);
        userRepository.save(entity);
    }

    @Transactional
    public UserLoginDto login(UserLoginDto dto){
        User user = userRepository.findByLogin(dto.getUsername())
                .orElseThrow(()
                        -> new UserNotFoundException("User with that login not found"));

        if(!CryptUtil.checkPassword(dto.getPassword(), user.getPassword())){
            throw new InvalidCredentialsException("Password is not correct");
        }

        return userMapper.toDto(user);
    }

    public SessionDto openSession(UserLoginDto userLoginDto) {
        return sessionService.findOrCreateSession(userLoginDto);
    }

    public void deleteSessionByUUID(UUID uuid){
        sessionService.deleteByUUID(uuid);
    }
}
