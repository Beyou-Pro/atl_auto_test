package com.ecommerce.controller.cart;

import com.ecommerce.model.cart.CartItem;
import com.ecommerce.model.cart.request.CartItemRequest;
import com.ecommerce.model.product.response.ProductResponse;
import com.ecommerce.service.product.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CartController.class)
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    private MockHttpSession session;

    @BeforeEach
    void setup() {
        session = new MockHttpSession();
    }

    @TestConfiguration
    static class MockConfig {
        @Bean
        public ProductService productService() {
            return Mockito.mock(ProductService.class);
        }
    }

    @Test
    void shouldInitializeEmptyCartAndGenerateCustomerUUID() throws Exception {
        mockMvc.perform(get("/cart").session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void shouldAddProductToCart() throws Exception {
        String productId = "prod-1";
        ProductResponse mockProduct = new ProductResponse(
                productId,
                "Phone",
                "Short description",
                "Long description",
                List.of("img1.jpg", "img2.jpg"),
                299.99,
                10
        );

        Mockito.when(productService.getProductById(productId)).thenReturn(mockProduct);

        CartItemRequest request = new CartItemRequest(productId, 2);

        mockMvc.perform(post("/cart/add")
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }


    @Test
    void shouldRemoveProductFromCart() throws Exception {
        String productId = "prod-1";

        List<CartItem> cart = new ArrayList<>();
        cart.add(new CartItem(productId, "Phone", 299.99, 1));
        session.setAttribute("cart", cart);

        mockMvc.perform(post("/cart/remove")
                        .param("productId", productId)
                        .session(session))
                .andExpect(status().isOk());

        mockMvc.perform(get("/cart").session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void shouldClearCart() throws Exception {
        List<CartItem> cart = new ArrayList<>();
        cart.add(new CartItem("prod-1", "Phone", 299.99, 1));
        session.setAttribute("cart", cart);

        mockMvc.perform(post("/cart/clear").session(session))
                .andExpect(status().isOk());

        mockMvc.perform(get("/cart").session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void shouldReturn404IfProductNotFound() throws Exception {
        Mockito.when(productService.getProductById(anyString())).thenReturn(null);

        CartItemRequest request = new CartItemRequest("invalid-prod", 1);

        mockMvc.perform(post("/cart/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .session(session))
                .andExpect(status().isNotFound());
    }
}
