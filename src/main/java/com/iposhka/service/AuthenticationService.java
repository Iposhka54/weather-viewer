package com.iposhka.service;

import com.iposhka.dto.CreateUserDto;
import com.iposhka.dto.SessionDto;
import com.iposhka.dto.UserDto;
import com.iposhka.exception.DatabaseException;
import com.iposhka.exception.InvalidCredentialsException;
import com.iposhka.exception.UserAlreadyExistException;
import com.iposhka.exception.UserNotFoundException;
import com.iposhka.mapper.UserMapper;
import com.iposhka.model.User;
import com.iposhka.repository.UserRepository;
import com.iposhka.util.CryptUtil;
import jakarta.transaction.Transactional;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public AuthenticationService(UserRepository userRepository, UserMapper userMapper){

        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional
    public void signUp(CreateUserDto userDto){
        User entity = userMapper.toEntity(userDto);
        String cryptPassword = CryptUtil.crypt(entity.getPassword());
        entity.setPassword(cryptPassword);
        try{
            userRepository.save(entity);
        }catch (ConstraintViolationException e){
            throw new UserAlreadyExistException("A user with this username already exists");
        }catch (Exception e){
            throw new DatabaseException("Any problems with database");
        }
    }

    public UserDto login(UserDto dto){
        Optional<User> maybeUser = userRepository.findByLogin(dto.getUsername());

        User user = maybeUser.orElseThrow(() -> new UserNotFoundException("User with that login not found"));

        if(!CryptUtil.checkPassword(dto.getPassword(), user.getPassword())){
            throw new InvalidCredentialsException("Password is not correct");
        }

        user.setPassword(dto.getPassword());

        return userMapper.toDto(user);
    }
}
