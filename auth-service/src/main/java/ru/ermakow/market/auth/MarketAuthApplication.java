package ru.ermakow.market.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MarketAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarketAuthApplication.class, args);
    }
}
//этот микросервис отвечает за хранение юзеров,их регистрацию, аутентификацию и работу с токенами
//(получаем запрос с логином/паролем и отдаем токены)