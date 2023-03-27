package ru.ermakow.market.auth.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ermakow.market.api.RegisterUserDto;
import ru.ermakow.market.api.StringResponse;
import ru.ermakow.market.auth.entities.User;
import ru.ermakow.market.auth.services.UserService;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class RegisterController {


    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> createNewUser(@RequestBody RegisterUserDto registerUserDto) {

        //обрабатываем несовпадение password-confirmPassword
        if(!registerUserDto.getPassword().equals(registerUserDto.getConfirmPassword())) {
            return ResponseEntity.ok(new StringResponse("PASSWORD_NOT_MATCHING"));
        }

        //@TODO создать свой RegistrationException + (можно сделать свой RegistrationValidator -> так как в идеале задача контроллера схватить запрос и отдать сервису, проверка уже будет там)
        //если юзер уже есть то обрабатываем это
        Optional<User> user = userService.getUserByUsername(registerUserDto.getUsername());
        if(user.isPresent()) {
            return ResponseEntity.ok(new StringResponse("USER EXISTS"));
        }

        //проверка email
        Optional<User> userWithEmail = userService.getUserByEmail(registerUserDto.getEmail());
        if(userWithEmail.isPresent()) {
            return ResponseEntity.ok(new StringResponse("USER EXISTS"));
        }

        //если все ок то пропускааем и создаем!!!
        userService.createNewUser(registerUserDto);
        return ResponseEntity.ok(new StringResponse("USER_CREATED"));
    }
}
