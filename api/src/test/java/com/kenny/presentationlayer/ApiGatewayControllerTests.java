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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;
import static org.springframework.http.HttpStatus.UNSUPPORTED_MEDIA_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON;

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


//    @Test
//    void shouldAddNewProduct(){
//        Product product = new Product();
//        product.setProduct_id(12);
//        product.setPrice(21.5);
//        product.setTitle("Mozart1");
//        product.setQuantity(2);
//        product.setCategory_id(3);
//
//        when(productServiceClient.createProduct(product)).thenReturn(Mono.just(product));
///*
//        client.get()
//                //check the URI
//                .uri("/api/gateway/product/1")
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody()
//                .jsonPath("$.product_id").isEqualTo(12)
//                .jsonPath("$.category_id").isEqualTo(3)
//                .jsonPath("$.title").isEqualTo("Mozart1")
//                .jsonPath("$.price").isEqualTo(21.5)
//                .jsonPath("$.quantity").isEqualTo(2);
//        */
//    }

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

    @Test
    @DisplayName("Create new Product")
    void shouldCreateProduct(){
        Product product = new Product();
        product.setProduct_id(1);
        product.setPrice(199.99);
        product.setCategory_id(5);
        product.setQuantity(66);
        product.setDescription("Test Description");
        product.setTitle("Test Product");

        when(productServiceClient.createProduct(product))
                .thenReturn(Mono.just(product));

        client.post()
                .uri("/api/gateway/newProduct/{product_id}", 1)
                .body(Mono.just(product), Product.class)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.product_id").isEqualTo(product.getProduct_id())
                .jsonPath("$.category_id").isEqualTo(product.getCategory_id())
                .jsonPath("$.price").isEqualTo(product.getPrice())
                .jsonPath("$.quantity").isEqualTo(product.getQuantity())
                .jsonPath("$.title").isEqualTo(product.getTitle())
                .jsonPath("$.description").isEqualTo(product.getDescription());


    }

    @Test
    @DisplayName("Body does not exist on post")
    void shouldThrowMediaTypeErrorIfBodyNotPresent(){
        Product product = new Product();
        product.setProduct_id(1);
        product.setPrice(199.99);
        product.setCategory_id(5);
        product.setQuantity(66);
        product.setDescription("Test Description");
        product.setTitle("Test Product");

        when(productServiceClient.createProduct(product))
                .thenReturn(Mono.just(product));

        client.post()
                .uri("/api/gateway/newProduct/{product_id}", product.getProduct_id())
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(UNSUPPORTED_MEDIA_TYPE)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.path").isEqualTo("/api/gateway/newProduct/" + product.getProduct_id());
    }

    @Test
    @DisplayName("Get Product by ID")
    void shouldGetProductByID(){
        Product product = new Product();
        product.setProduct_id(1);
        product.setPrice(199.99);
        product.setCategory_id(5);
        product.setQuantity(66);
        product.setDescription("Test Description");
        product.setTitle("Test Product");

        when(productServiceClient.getProductByID(product.getProduct_id()))
                .thenReturn(Mono.just(product));

        client.get()
                .uri("/api/gateway/products/{product_id}", product.getProduct_id())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.product_id").isEqualTo(product.getProduct_id())
                .jsonPath("$.category_id").isEqualTo(product.getCategory_id())
                .jsonPath("$.price").isEqualTo(product.getPrice())
                .jsonPath("$.quantity").isEqualTo(product.getQuantity())
                .jsonPath("$.title").isEqualTo(product.getTitle())
                .jsonPath("$.description").isEqualTo(product.getDescription());
    }

    @Test
    @DisplayName("Get Missing Path")
    void shouldThrowErrorOnMissingPath(){
        client.get()
                .uri("/products")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.path").isEqualTo("/products")
                .jsonPath("$.message").isEqualTo(null);
    }

    @Test
    @DisplayName("ProductsNotFound")
    void shouldThrowErrorOnProductNotFound(){
        client.get()
                .uri("/products/{product_id}", 1000)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.path").isEqualTo("/products/1000")
                .jsonPath("$.message").isEqualTo(null);

    }

    @Test
    @DisplayName("Delete Product By Id")
    void shouldDeleteProductByID(){
        Product product = new Product();
        product.setProduct_id(1);
        product.setPrice(199.99);
        product.setCategory_id(5);
        product.setQuantity(66);
        product.setDescription("Test Description");
        product.setTitle("Test Product");

        when(productServiceClient.createProduct(product))
                .thenReturn(Mono.just(product));

        client.post()
                .uri("/api/gateway/newProduct/{product_id}", 1)
                .body(Mono.just(product), Product.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody();

        assertEquals(Optional.ofNullable(product.getProduct_id()),Optional.ofNullable(1));

        client.delete()
                .uri("/api/gateway/products/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody();

        assertEquals(null, productServiceClient.getProductByID(product.getProduct_id()));
    }

    @Test
    @DisplayName("Get All Products")
    void shouldGetAllRoles(){
        Product product = new Product();
        product.setProduct_id(1);
        product.setPrice(199.99);
        product.setCategory_id(5);
        product.setQuantity(66);
        product.setDescription("Test Description");
        product.setTitle("Test Product");

        Product product1 = new Product();
        product1.setProduct_id(2);
        product1.setPrice(250);
        product1.setCategory_id(9);
        product1.setQuantity(31);
        product1.setDescription("Test Description for product1");
        product1.setTitle("Test Product1");

        List<Product> productList = new ArrayList<>();
        productList.add(product);
        productList.add(product1);

        Flux<Product> allProducts = Flux.fromIterable(productList);

        when(productServiceClient.getAllProducts())
                .thenReturn(allProducts);

        client.get()
                .uri("/api/gateway/products")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].product_id").isEqualTo(product.getProduct_id())
                .jsonPath("$[0].category_id").isEqualTo(product.getCategory_id())
                .jsonPath("$[0].price").isEqualTo(product.getPrice())
                .jsonPath("$[0].quantity").isEqualTo(product.getQuantity())
                .jsonPath("$[0].title").isEqualTo(product.getTitle())
                .jsonPath("$[0].description").isEqualTo(product.getDescription())
                .jsonPath("$[1].product_id").isEqualTo(product1.getProduct_id())
                .jsonPath("$[1].category_id").isEqualTo(product1.getCategory_id())
                .jsonPath("$[1].price").isEqualTo(product1.getPrice())
                .jsonPath("$[1].quantity").isEqualTo(product1.getQuantity())
                .jsonPath("$[1].title").isEqualTo(product1.getTitle())
                .jsonPath("$[1].description").isEqualTo(product1.getDescription());
    }

    @Test
    @DisplayName("Update Product")
    void shouldUpdateProductById(){
        Product product = new Product();
        product.setProduct_id(1);
        product.setPrice(199.99);
        product.setCategory_id(5);
        product.setQuantity(66);
        product.setDescription("Test Description");
        product.setTitle("Test Product");

        Product product1 = new Product();
        product1.setProduct_id(1);
        product1.setPrice(250);
        product1.setCategory_id(9);
        product1.setQuantity(31);
        product1.setDescription("Test Description for product1");
        product1.setTitle("Test Product1");

        when(productServiceClient.createProduct(product))
                .thenReturn(Mono.just(product));

        client.post()
                .uri("/api/gateway/newProduct/{product_id}", 1)
                .body(Mono.just(product), Product.class)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.product_id").isEqualTo(product.getProduct_id())
                .jsonPath("$.category_id").isEqualTo(product.getCategory_id())
                .jsonPath("$.price").isEqualTo(product.getPrice())
                .jsonPath("$.quantity").isEqualTo(product.getQuantity())
                .jsonPath("$.title").isEqualTo(product.getTitle())
                .jsonPath("$.description").isEqualTo(product.getDescription());

        when(productServiceClient.updateProduct(product.getProduct_id(),product1))
                .thenReturn(Mono.just(product));

        client.put()
                .uri("/api/gateway/products/{product_id}", product.getProduct_id())
                .body(Mono.just(product1), Product.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody();

        assertEquals(productServiceClient.getProductByID(1),null);

    }

}
