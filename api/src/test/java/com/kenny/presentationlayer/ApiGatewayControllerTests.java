package com.kenny.presentationlayer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenny.domainclientlayer.ProductServiceClient;
import com.kenny.dtos.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = ApiGatewayController.class)
@AutoConfigureWebTestClient
class ApiGatewayControllerTests {

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductServiceClient productServiceClient;

    @Autowired
    private WebTestClient client;


    @Test
    void shouldAddNewProduct(){
        Product product = new Product();
        product.setProduct_id(12);
        product.setPrice(21.5);
        product.setTitle("Mozart1");
        product.setQuantity(2);
        product.setCategory_id(3);

        when(productServiceClient.createProduct(product)).thenReturn(Mono.just(product));
/*
        client.get()
                //check the URI
                .uri("/api/gateway/product/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.product_id").isEqualTo(12)
                .jsonPath("$.category_id").isEqualTo(3)
                .jsonPath("$.title").isEqualTo("Mozart1")
                .jsonPath("$.price").isEqualTo(21.5)
                .jsonPath("$.quantity").isEqualTo(2);
        */
    }

/*
    @Test
    @DisplayName("Shouldn't return null when getAllProducts is issued")
    void shouldntReturnNullForGetAllProducts(){
       Flux<Product> list = productServiceClient.getAllProducts();

        when(productServiceClient.getAllProducts())
                .thenReturn(list);

        Assertions.assertNotNull(list.collectList().block());

        Assertions.assertNotNull(productServiceClient.getAllProducts());
    }
*/
/*
    @Test
    void shouldReturnTheCorrectFirst3Products() {

        Flux<Product> list = productServiceClient.getAllProducts();
        when(productServiceClient.getAllProducts())
                .thenReturn(list);

        client.get()
                .uri("/api/gateway/products")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.product_id").isEqualTo(1)
                .jsonPath("$.category_id").isEqualTo(7)
                .jsonPath("$.title").isEqualTo("Mozart1")
                .jsonPath("$.price").isEqualTo(32.44)
                .jsonPath("$.quantity").isEqualTo(1);


    }
    */

}
