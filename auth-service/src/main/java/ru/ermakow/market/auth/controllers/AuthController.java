package ru.ermakow.market.auth.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.ermakow.market.api.JwtRequest;
import ru.ermakow.market.api.JwtResponse;
import ru.ermakow.market.auth.exceptions.AppError;
import ru.ermakow.market.auth.services.UserService;
import ru.ermakow.market.auth.utils.JwtTokenUtil;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest jwtRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.ok(new AppError("CHECK_TOKEN_ERROR", e.getMessage()));
        }

        UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }
}

//логика контроллера -> на эндпойнт /auth приходит json c username/password, в методе createAuthToken первым делом в блоке
//try-catch проходит проверка: authenticationManager'у передается UsernamePasswordAuthenticationToken c логином и паролем, если
//ошибка то перехватывается badCredentialsException и возвращается ResponseEntity. Если все ок то достаем userDetails, и далее утилитный класс
//JwtTokenUtil сгенерирует токен
