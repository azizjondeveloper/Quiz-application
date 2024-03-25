package com.example.quizapplication.auth;


import com.example.quizapplication.entity.role.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String firstname;

    private String lastname;

    @NotBlank(message = "emailda xatolik")
    @Email(message = "emailda xatolik", regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")
    private String email;


    @NotBlank(message = "parolda xatolik")
    @Pattern(message = "parolda xatolik",regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+=])(?=\\S+$).{8,}$")
    private String password;

    private Role role;
}
