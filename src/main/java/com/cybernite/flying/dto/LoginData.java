package com.cybernite.flying.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@Validated
@NoArgsConstructor
public class LoginData {
    @Email
    private String email;
    @NotEmpty
    private String password;
}
