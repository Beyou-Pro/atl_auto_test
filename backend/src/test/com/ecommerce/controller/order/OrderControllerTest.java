package com.ecommerce.controller.order;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldRejectOrderWhenProductIsOutOfStock() throws Exception {
        String jsonOrder = """
        {
            "customerId": "123e4567-e89b-12d3-a456-426614174000",
            "orderItems": [
                { "productId": "e0a1b2c3-d4e5-6f7a-8b9c-0d1e2f3a4b5c", "quantity": 99 }
            ],
            "billingAddress": {
                "street": "1 rue",
                "city": "Paris",
                "zipcode": "75000",
                "country": "France",
                "addressType": "BILLING"
            },
            "shippingAddress": {
                "street": "1 rue",
                "city": "Paris",
                "zipcode": "75000",
                "country": "France",
                "addressType": "SHIPPING"
            },
            "carrierId": "trk001",
            "paymentId": "pay001"
        }
        """;

        mockMvc.perform(post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonOrder))
                .andExpect(status().is2xxSuccessful());
    }
}
