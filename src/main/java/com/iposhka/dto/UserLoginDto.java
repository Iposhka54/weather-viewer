package com.iposhka.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDto {
    private Integer id;

    @NotNull(message = "Username cannot be empty")
    @Size(min = 3, max = 128, message = "Username must be between 3 and 128 characters")
    @Pattern(regexp = "^[A-Za-z0-9_-]{3,128}$|^[A-Za-z0-9]+(?:[A-Za-z0-9\\.])*@[A-Za-z0-9]+(?:[A-Za-z0-9\\.])*\\.[A-Za-z]{2,}$", message = "Username need include latin symbols, digits and symbols: -_")
    private String username;

    @NotNull(message = "Password cannot be empty")
    @Size(min = 8, max = 128, message = "Password must be between 8 and 128 characters")
    @Pattern(regexp = ".*[0-9].*", message = "Password must contain at least one digit")
    @Pattern(regexp = ".*[A-Z].*", message = "Password must contain at least one uppercase letter")
    @Pattern(regexp = ".*[a-z].*", message = "Password must contain at least one lowercase letter")
    @Pattern(regexp = ".*[!()\\-_/\\@.*?^%$#~`+|].*", message = "Password must contain at least one special character (!()-_/\\@.*?^%$#~`+|)")
    @Pattern(regexp = "^[A-Za-z0-9!()\\-_/\\@.*?^%$#`~+|]+$", message = "Password contains invalid characters")
    private String password;

    private List<LocationResponseDto> locations;
}
