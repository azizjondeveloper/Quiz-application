package com.example.quizapplication.auth;

import com.example.quizapplication.payload.ApiResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RequestMapping(AuthController.AUTH_CONTROLLER_PATH)
public interface AuthController {


    String AUTH_CONTROLLER_PATH = "/api/v1/auth";

    String VERIFICATION_EMAIL_PATH = "/verification-email";

    String SIGN_UP_PATH = "/sign-up";

    String SIGN_IN_PATH = "/sign-in";

    String REFRESH_TOKEN = "/refresh-token";


    @PostMapping(SIGN_UP_PATH)
    ApiResult<?> register(@Valid @RequestBody RegisterRequest request);



    @PostMapping(SIGN_IN_PATH)
    ApiResult<?> signIn(@Valid @RequestBody SignInRequest request);


    @GetMapping(value = VERIFICATION_EMAIL_PATH)
    ApiResult<?> verificationEmail(@RequestParam UUID code);



    @PostMapping(REFRESH_TOKEN)
    void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException;

}
