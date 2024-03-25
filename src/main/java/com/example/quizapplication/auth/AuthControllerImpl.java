package com.example.quizapplication.auth;

import com.example.quizapplication.payload.ApiResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;
    @Override
    public ApiResult<?> register(RegisterRequest request) {
        return authService.register(request);
    }

    @Override
    public ApiResult<?> signIn(SignInRequest request) {
        return authService.signIn(request);
    }

    @Override
    public ApiResult<?> verificationEmail(UUID code) {
        return authService.verificationEmail(code);
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }
}
