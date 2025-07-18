package com.ecommerce.e2e.order;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 3000)
class OrderE2ETest {

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void setupMockProductService() {
        WireMock.stubFor(get(urlEqualTo("/products/e0a1b2c3-d4e5-6f7a-8b9c-0d1e2f3a4b5c"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                    {
                                        "id": "e0a1b2c3-d4e5-6f7a-8b9c-0d1e2f3a4b5c",
                                        "name": "T-shirt Coton Bio Blanc",
                                        "description_short": "Un essentiel doux et respectueux de l'environnement.",
                                        "description_long": "Ce t-shirt blanc en coton 100% biologique offre une sensation douce et confortable. Un basique indispensable pour un style décontracté et éco-responsable.",
                                        "price": 25,
                                        "currency": "EUR",
                                        "stock": 170,
                                        "category": "t-shirt",
                                        "weight": 200,
                                        "images": [
                                            "https://example.com/images/tshirt_bio_blanc_1.jpg",
                                            "https://example.com/images/tshirt_bio_blanc_2.jpg"
                                        ]
                                    }
                                """)
                )
        );
    }

    @Test
    void shouldCreateOrderSuccessfully() {
        String jsonOrder = """
            {
                "customerId": "123e4567-e89b-12d3-a456-426614174000",
                "orderItems": [
                    { "productId": "e0a1b2c3-d4e5-6f7a-8b9c-0d1e2f3a4b5c", "quantity": 2 }
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

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(jsonOrder, headers);

        var response = restTemplate.postForEntity("/order", entity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("e0a1b2c3-d4e5-6f7a-8b9c-0d1e2f3a4b5c");
    }


//    @Test
//    void shouldRejectOrderWhenProductOutOfStock() {
//        WireMock.stubFor(get(urlEqualTo("/products/e0a1b2c3-d4e5-6f7a-8b9c-0d1e2f3a4b5c"))
//                .willReturn(aResponse()
//                        .withHeader("Content-Type", "application/json")
//                        .withBody("""
//                                    {
//                                        "id": "product-2",
//                                        "name": "Low Stock Product",
//                                        "description_short": "desc",
//                                        "description_long": "desc",
//                                        "images": [],
//                                        "price": 19.99,
//                                        "stock": 1
//                                    }
//                                """)));
//
//        String jsonOrder = """
//                {
//                    "customerId": "123e4567-e89b-12d3-a456-426614174000",
//                    "orderItems": [
//                        { "productId": "product-2", "quantity": 3 }
//                    ],
//                    "billingAddress": {
//                        "street": "1 rue",
//                        "city": "Paris",
//                        "zipcode": "75000",
//                        "country": "France",
//                        "addressType": "BILLING"
//                    },
//                    "shippingAddress": {
//                        "street": "1 rue",
//                        "city": "Paris",
//                        "zipcode": "75000",
//                        "country": "France",
//                        "addressType": "SHIPPING"
//                    },
//                    "carrierId": "carrier1",
//                    "paymentId": "payment1"
//                }
//                """;
//
//        var response = restTemplate.postForEntity("/order", jsonOrder, String.class);
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
//        assertThat(response.getBody()).contains("rupture de stock");
//    }
}

