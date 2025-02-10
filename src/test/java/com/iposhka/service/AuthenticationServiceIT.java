package com.iposhka.service;

import com.iposhka.config.TestConfig;
import com.iposhka.dto.CreateUserDto;
import com.iposhka.dto.UserLoginDto;
import com.iposhka.exception.EntityAlreadyExistException;
import com.iposhka.exception.InvalidCredentialsException;
import com.iposhka.exception.UserNotFoundException;
import com.iposhka.model.User;
import com.iposhka.repository.UserRepository;
import com.iposhka.util.CryptUtil;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

@Transactional
@ActiveProfiles("test")
@WebAppConfiguration
@SpringJUnitConfig(classes = TestConfig.class)
class AuthenticationServiceIT {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationService authService;

    @Nested
    class SignUpTests {

        @Test
        void userIsSavedToDatabaseOnRegistration() {
            String username = "iposhka";
            String password = "Ke1234578!";

            CreateUserDto iposhka = CreateUserDto.builder()
                    .username(username)
                    .password(password)
                    .repeatedPassword(password)
                    .build();

            authService.signUp(iposhka);

            Optional<User> actualResult = userRepository.findByLogin(username);

            assertThat(actualResult)
                    .as("User should be saved in the database")
                    .isPresent();
            User user = actualResult.get();

            assertThat(user.getLogin())
                    .as("Username should match the input value")
                    .isEqualTo(username);

            assertThat(user.getPassword())
                    .as("Password should be hashed and not match the original input")
                    .isNotEqualTo(password);

            assertThat(CryptUtil.checkPassword(password, user.getPassword()))
                    .as("Hashed password should correspond to the original input password")
                    .isTrue();
        }

        @Test
        void throwExceptionIfPasswordsDontMatch() {
            String username = "iposhka";
            String password = "Ke12345678!";
            String repeatedPassword = "dummy";

            CreateUserDto iposhka = CreateUserDto.builder()
                    .username(username)
                    .password(password)
                    .repeatedPassword(repeatedPassword)
                    .build();

            assertThatThrownBy(() -> authService.signUp(iposhka)).isInstanceOf(InvalidCredentialsException.class);
        }

        @Test
        @Sql("/db/insert-data.sql")
        void throwExceptionIfUserAlreadyExist() {
            String username = "iposhka";
            String password = "Ke12345678!";

            CreateUserDto iposhka = CreateUserDto.builder()
                    .username(username)
                    .password(password)
                    .repeatedPassword(password)
                    .build();

            assertThatThrownBy(() -> authService.signUp(iposhka)).isInstanceOf(EntityAlreadyExistException.class);
        }
    }

    @Nested
    class LoginTests{
        @Test
        void userSuccessfullyLogin() {
            String username = "iposhka";
            String password = "securepassword";

            User user = User.builder()
                    .login(username)
                    .password(CryptUtil.crypt(password))
                    .build();

            userRepository.save(user);

            UserLoginDto userLoginDto = UserLoginDto.builder()
                    .username(username)
                    .password(password)
                    .build();

            UserLoginDto actualResult = authService.login(userLoginDto);

            assertThat(actualResult)
                    .as("Login should return a UserLoginDto")
                    .isNotNull();
            assertThat(actualResult.getUsername())
                    .as("Username in result should match the input")
                    .isEqualTo(username);
        }

        @Test
        void throwExceptionIfUserNotFound() {
            String username = "dummy";
            String password = "Ke1234578!";

            UserLoginDto userLoginDto = UserLoginDto.builder()
                    .username(username)
                    .password(password)
                    .build();

            assertThatThrownBy(() -> authService.login(userLoginDto))
                    .as("User should not exist")
                    .isInstanceOf(UserNotFoundException.class);
        }

        @Test
        void throwExceptionIfPasswordsDontMatch() {
            String username = "iposhka";
            String password = "securepassword";

            User user = User.builder()
                    .login(username)
                    .password(CryptUtil.crypt(password))
                    .build();

            userRepository.save(user);

            UserLoginDto userLoginDto = UserLoginDto.builder()
                    .username(username)
                    .password("dummy")
                    .build();

            assertThatThrownBy(() -> authService.login(userLoginDto))
                    .as("Password should not correct")
                    .isInstanceOf(InvalidCredentialsException.class);
        }
    }
}