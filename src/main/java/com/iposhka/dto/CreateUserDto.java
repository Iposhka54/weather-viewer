package com.iposhka.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDto {
    @NotNull
    @Length(min = 3, max = 128)
    @Pattern(regexp = "^[A-Za-z0-9_-]{3,128}$|^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\\\.[A-Za-z]{2,}$")
    private String username;
    @NotNull
    @Length(min = 8, max = 128)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[!()\\-_/\\\\@.*?^%$#~`+|])[A-Za-z0-9!()\\-_/\\\\@.*?^%$#`~+|]{8,128}$")
    private String password;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[!()\\-_/\\\\@.*?^%$#~`+|])[A-Za-z0-9!()\\-_/\\\\@.*?^%$#`~+|]{8,128}$")
    private String repeatedPassword;
}
