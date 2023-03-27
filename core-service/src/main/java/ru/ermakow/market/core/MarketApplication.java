package ru.ermakow.market.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarketApplication.class, args);
	}
}

// для подключения swagger подключаем зависимость springdoc-openapi-ui и делаем минимальную конфигурацию -> OpenApiConfig
// после этого появляется страничка с документацией swagger-ui/index.html
// пример разметки в ProductController и ProductDto