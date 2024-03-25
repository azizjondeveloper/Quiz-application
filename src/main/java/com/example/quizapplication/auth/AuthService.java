package com.example.quizapplication.auth;

import com.example.quizapplication.payload.ApiResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

public interface AuthService {

    ApiResult<String> register(RegisterRequest request);


    ApiResult<?> signIn(SignInRequest request);


    ApiResult<String> verificationEmail(UUID code);

    void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException;
}
