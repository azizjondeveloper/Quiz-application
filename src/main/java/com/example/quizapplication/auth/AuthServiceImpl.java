package com.example.quizapplication.auth;

import com.example.quizapplication.entity.User;
import com.example.quizapplication.exception.DataNotfoundException;
import com.example.quizapplication.exception.InputDataExistsException;
import com.example.quizapplication.payload.ApiResult;
import com.example.quizapplication.repository.UserRepository;
import com.example.quizapplication.security.JwtService;
import com.example.quizapplication.service.EmailService;
import com.example.quizapplication.token.Token;
import com.example.quizapplication.token.TokenRepository;
import com.example.quizapplication.token.TokenType;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements UserDetailsService, AuthService {

    private final UserRepository userRepository;

    private final TokenRepository tokenRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final EmailService emailService;

    private final AuthenticationManager authenticationManager;




    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Bunday %s topilmadi", username)));
    }


    @Override
    public ApiResult<String> register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getEmail()))
            throw new InputDataExistsException("Bunday email mavjud");

        User user = new User(request.getFirstname(), request.getLastname(),
                request.getEmail(), passwordEncoder.encode(request.getPassword()), request.getRole());


        UUID emailCode = UUID.randomUUID();
        user.setEmailCode(emailCode);
        userRepository.save(user);
        new Thread(() -> emailService.sendEmailVerificationCode(user.getUsername(), emailCode)).start();

        User savedUser = userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        saveUserToken(savedUser, jwtToken);

        return ApiResult.successResponse();
    }

    @Override
    public ApiResult<?> signIn(SignInRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        User user = userRepository.findByUsername(request.getEmail())
                .orElseThrow();
        String  jwtToken = jwtService.generateToken(user);
        String  refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        SignInResponse response = SignInResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();

        return ApiResult.successResponse(response);
    }



    @Override
    public ApiResult<String> verificationEmail(UUID code) {
        User user = userRepository.findByEmailCode(code)
                .orElseThrow(() -> new DataNotfoundException("Bunday code mavjud emas"));

        user.setEmailCode(null);
        user.setEnabled(true);
        userRepository.save(user);

        return ApiResult.successResponse("Muvaffaqiyatli activ qilindi");

    }


    private void revokeAllUserTokens(User user) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }





    private void saveUserToken(User user, String jwtToken) {
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }



    @Override
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response)    throws IOException {

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;

        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }

        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);

        if (userEmail != null) {
            User user = this.userRepository.findByUsername(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                String accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                SignInResponse signInResponse = SignInResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), signInResponse);
            }
        }
    }


}
