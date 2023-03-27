package ru.ermakow.market.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.ermakow.market.api.ProductDto;

import java.math.BigDecimal;

@SpringBootTest
@AutoConfigureMockMvc //эмулирует поведение вэба
public class ProductControllerTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getAllProducts() throws Exception {
        mvc
                .perform(
                        MockMvcRequestBuilders.get("/api/v1/products").contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Bread"));
    }

    @Test
    public void getProductById() throws Exception {
        mvc
                .perform(
                        MockMvcRequestBuilders.get("/api/v1/products/4").contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Cheese"));
    }

    @Test
    public void createProductTest() throws Exception {
        ProductDto productDto = new ProductDto(null, "Demo", BigDecimal.valueOf(999.99), "Food");

        mvc
                .perform(
                        MockMvcRequestBuilders.post("/api/v1/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(productDto))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
}
