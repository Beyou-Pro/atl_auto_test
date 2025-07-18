package com.ecommerce.service.orderItem;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.List;

import com.ecommerce.entity.orderitem.OrderItem;
import com.ecommerce.model.orderitem.request.OrderItemRequest;
import com.ecommerce.model.product.response.ProductResponse;
import com.ecommerce.repository.product.ProductRepository;
import com.ecommerce.service.orderitem.impl.OrderItemServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

class OrderItemServiceTest {

    @Mock
    private ProductRepository productRepository;

    private OrderItemServiceImpl orderItemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderItemService = new OrderItemServiceImpl(productRepository);
    }

    @Test
    void givenOrderItemRequests_whenCreateOrderItems_thenReturnCorrectOrderItems() {
        String productId1 = "e0a1b2c3-d4e5-6f7a-8b9c-0d1e2f3a4b5c";
        String productId2 = "f1b2c3d4-e5f6-7a8b-9c0d-1e2f3a4b5c6d";

        OrderItemRequest request1 = new OrderItemRequest(productId1, 2);
        OrderItemRequest request2 = new OrderItemRequest(productId2, 3);

        ProductResponse product1 = new ProductResponse(
                productId1,
                "T-shirt Coton Bio Blanc",
                "Un essentiel doux et respectueux de l'environnement.",
                "Ce t-shirt blanc en coton 100% biologique offre une sensation douce et confortable. Un basique indispensable pour un style décontracté et éco-responsable.",
                List.of(
                        "https://example.com/images/tshirt_bio_blanc_1.jpg",
                        "https://example.com/images/tshirt_bio_blanc_2.jpg"
                ),
                25.0,
                170
        );

        ProductResponse product2 = new ProductResponse(
                productId2,
                "T-shirt Imprimé Graphique 'Voyage'",
                "Design unique inspiré du voyage et de l'aventure.",
                "Affichez votre esprit d'aventure avec ce t-shirt doté d'un imprimé graphique original. Idéal pour ceux qui aiment explorer le monde et les styles audacieux.",
                List.of(
                        "https://example.com/images/tshirt_voyage_1.jpg",
                        "https://example.com/images/tshirt_voyage_2.jpg"
                ),
                35.5,
                120
        );

        when(productRepository.getProductById(productId1)).thenReturn(product1);
        when(productRepository.getProductById(productId2)).thenReturn(product2);

        List<OrderItem> orderItems = orderItemService.createOrderItems(List.of(request1, request2));

        assertThat(orderItems).hasSize(2);

        OrderItem item1 = orderItems.get(0);
        assertThat(item1.getProductId()).isEqualTo(productId1);
        assertThat(item1.getQuantity()).isEqualTo(2);
        assertThat(item1.getUnitPrice()).isEqualTo(25.0);
        assertThat(item1.getTotalPrice()).isEqualTo(50.0);

        OrderItem item2 = orderItems.get(1);
        assertThat(item2.getProductId()).isEqualTo(productId2);
        assertThat(item2.getQuantity()).isEqualTo(3);
        assertThat(item2.getUnitPrice()).isEqualTo(35.5);
        assertThat(item2.getTotalPrice()).isEqualTo(106.5);

        verify(productRepository, times(1)).getProductById(productId1);
        verify(productRepository, times(1)).getProductById(productId2);
    }

}

