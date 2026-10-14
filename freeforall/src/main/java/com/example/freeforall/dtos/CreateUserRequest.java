package com.example.freeforall.dtos;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.*;

@Getter
@Setter
public class CreateUserRequest {

    @NotBlank
    @Size(min = 4, message = "Username should be at least 4 characters")
    private String username;

    @Email()
    private String email;

    @Size(min = 8, message = "Password should be at least 8 characters")
    @Pattern(regexp = ".*[0-9].*", message = "Password must contain at least one number")
    @Pattern(regexp = ".*[A-Z].*", message = "Password must contain at least one uppercase letter")
    @Pattern(regexp = ".*[@#$%^&+=!].*", message = "Password must contain at least one special character")
    private String password;

}
