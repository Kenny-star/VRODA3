package com.kenny.presentationlayer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenny.domainclientlayer.CartServiceClient;
import com.kenny.domainclientlayer.ProductServiceClient;
import com.kenny.dtos.Cart;
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
import java.util.UUID;

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

    @MockBean
    private CartServiceClient cartServiceClient;

    @Autowired
    private WebTestClient client;


    @Test
    @DisplayName("Create new Product")
    void shouldCreateProduct(){
        Product product = new Product();
        product.setProductId(UUID.randomUUID().toString());
        product.setPrice(199.99);
        product.setCategoryId(5);
        product.setQuantity(66);
        product.setDescription("Test Description");
        product.setTitle("Test Product");

        when(productServiceClient.createProduct(product))
                .thenReturn(Mono.just(product));

        client.post()
                .uri("/api/gateway/newProduct")
                .body(Mono.just(product), Product.class)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.productId").isEqualTo(product.getProductId())
                .jsonPath("$.categoryId").isEqualTo(product.getCategoryId())
                .jsonPath("$.price").isEqualTo(product.getPrice())
                .jsonPath("$.quantity").isEqualTo(product.getQuantity())
                .jsonPath("$.title").isEqualTo(product.getTitle())
                .jsonPath("$.description").isEqualTo(product.getDescription());


    }

    @Test
    @DisplayName("Body does not exist on post")
    void shouldThrowMediaTypeErrorIfBodyNotPresent(){
        Product product = new Product();
        product.setProductId(UUID.randomUUID().toString());
        product.setPrice(199.99);
        product.setCategoryId(5);
        product.setQuantity(66);
        product.setDescription("Test Description");
        product.setTitle("Test Product");

        when(productServiceClient.createProduct(product))
                .thenReturn(Mono.just(product));

        client.post()
                .uri("/api/gateway/newProduct")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(UNSUPPORTED_MEDIA_TYPE)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.path").isEqualTo("/api/gateway/newProduct");
    }

    @Test
    @DisplayName("Get Product by ID")
    void shouldGetProductByID(){
        Product product = new Product();
        product.setProductId(UUID.randomUUID().toString());
        product.setPrice(199.99);
        product.setCategoryId(5);
        product.setQuantity(66);
        product.setDescription("Test Description");
        product.setTitle("Test Product");

        when(productServiceClient.getProductByID(product.getProductId()))
                .thenReturn(Mono.just(product));

        client.get()
                .uri("/api/gateway/products/{product_id}", product.getProductId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.productId").isEqualTo(product.getProductId())
                .jsonPath("$.categoryId").isEqualTo(product.getCategoryId())
                .jsonPath("$.price").isEqualTo(product.getPrice())
                .jsonPath("$.quantity").isEqualTo(product.getQuantity())
                .jsonPath("$.title").isEqualTo(product.getTitle())
                .jsonPath("$.description").isEqualTo(product.getDescription());

        System.out.print("\n" +"/api/gateway/products/" + product.getProductId() + "\n");
        System.out.printf("/api/gateway/products/%s" + "\n", product.getProductId());
        System.out.println(productServiceClient.getProductByID(product.getProductId()).block().toString());

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
        product.setProductId(UUID.randomUUID().toString());
        product.setPrice(199.99);
        product.setCategoryId(5);
        product.setQuantity(66);
        product.setDescription("Test Description");
        product.setTitle("Test Product");

        when(productServiceClient.createProduct(product))
                .thenReturn(Mono.just(product));

        client.post()
                .uri("/api/gateway/newProduct")
                .body(Mono.just(product), Product.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody();

        assertEquals(Optional.ofNullable(product.getProductId()),Optional.ofNullable(product.getProductId()));

        client.delete()
                .uri("/api/gateway/products/{product_id}", product.getProductId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody();

        assertEquals(null, productServiceClient.getProductByID(product.getProductId()));
    }

    @Test
    @DisplayName("Get All Products")
    void shouldGetAllProducts(){
        Product product = new Product();
        product.setProductId(UUID.randomUUID().toString());
        product.setPrice(199.99);
        product.setCategoryId(5);
        product.setQuantity(66);
        product.setDescription("Test Description");
        product.setTitle("Test Product");

        Product product1 = new Product();
        product1.setProductId(UUID.randomUUID().toString());
        product1.setPrice(250);
        product1.setCategoryId(9);
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
                .jsonPath("$[0].productId").isEqualTo(product.getProductId())
                .jsonPath("$[0].categoryId").isEqualTo(product.getCategoryId())
                .jsonPath("$[0].price").isEqualTo(product.getPrice())
                .jsonPath("$[0].quantity").isEqualTo(product.getQuantity())
                .jsonPath("$[0].title").isEqualTo(product.getTitle())
                .jsonPath("$[0].description").isEqualTo(product.getDescription())
                .jsonPath("$[1].productId").isEqualTo(product1.getProductId().toString())
                .jsonPath("$[1].categoryId").isEqualTo(product1.getCategoryId())
                .jsonPath("$[1].price").isEqualTo(product1.getPrice())
                .jsonPath("$[1].quantity").isEqualTo(product1.getQuantity())
                .jsonPath("$[1].title").isEqualTo(product1.getTitle())
                .jsonPath("$[1].description").isEqualTo(product1.getDescription());
    }

    @Test
    @DisplayName("Update Product")
    void shouldUpdateProductById(){
        Product product = new Product();
        product.setProductId(UUID.randomUUID().toString());
        product.setPrice(199.99);
        product.setCategoryId(5);
        product.setQuantity(66);
        product.setDescription("Test Description");
        product.setTitle("Test Product");

        Product product1 = new Product();
        product1.setProductId(UUID.randomUUID().toString());
        product1.setPrice(250);
        product1.setCategoryId(9);
        product1.setQuantity(31);
        product1.setDescription("Test Description for product1");
        product1.setTitle("Test Product1");

        when(productServiceClient.createProduct(product))
                .thenReturn(Mono.just(product));

        client.post()
                .uri("/api/gateway/newProduct")
                .body(Mono.just(product), Product.class)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.productId").isEqualTo(product.getProductId())
                .jsonPath("$.categoryId").isEqualTo(product.getCategoryId())
                .jsonPath("$.price").isEqualTo(product.getPrice())
                .jsonPath("$.quantity").isEqualTo(product.getQuantity())
                .jsonPath("$.title").isEqualTo(product.getTitle())
                .jsonPath("$.description").isEqualTo(product.getDescription());

        when(productServiceClient.updateProduct(product1))
                .thenReturn(Mono.just(product));

        client.put()
                .uri("/api/gateway/products/{product_id}", product.getProductId())
                .body(Mono.just(product1), Product.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody();

        assertEquals(productServiceClient.getProductByID(product.getProductId()),null);

    }

    @Test
    @DisplayName("Get Product by Title")
    void shouldGetProductByTitle(){
        Product product = new Product();
        product.setProductId(UUID.randomUUID().toString());
        product.setPrice(199.99);
        product.setCategoryId(5);
        product.setQuantity(66);
        product.setDescription("Test Description");
        product.setTitle("Test Product");

        Product product1 = new Product();
        product1.setProductId(UUID.randomUUID().toString());
        product1.setPrice(250);
        product1.setCategoryId(6);
        product1.setQuantity(31);
        product1.setDescription("Test Description for product1");
        product1.setTitle("Test Product1");

        Product product2 = new Product();
        product2.setProductId(UUID.randomUUID().toString());
        product2.setPrice(250);
        product2.setCategoryId(7);
        product2.setQuantity(31);
        product2.setDescription("Test Description for product1");
        product2.setTitle("Test Product1");

//        List<Product> productList = new ArrayList<>();
//        productList.add(product);
//        productList.add(product1);
//
//        Flux<Product> allProducts = Flux.fromIterable(productList);

        when(productServiceClient.getProductByTitle("Test Product1"))
                .thenReturn(Flux.just(product1, product2));

        client.get()
                .uri("/api/gateway/products/title/{title}", product1.getTitle())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].productId").isEqualTo(product1.getProductId())
                .jsonPath("$[0].categoryId").isEqualTo(product1.getCategoryId())
                .jsonPath("$[0].price").isEqualTo(product1.getPrice())
                .jsonPath("$[0].quantity").isEqualTo(product1.getQuantity())
                .jsonPath("$[0].title").isEqualTo(product1.getTitle())
                .jsonPath("$[0].description").isEqualTo(product1.getDescription())
                .jsonPath("$[1].productId").isEqualTo(product2.getProductId())
                .jsonPath("$[1].categoryId").isEqualTo(product2.getCategoryId())
                .jsonPath("$[1].price").isEqualTo(product2.getPrice())
                .jsonPath("$[1].quantity").isEqualTo(product2.getQuantity())
                .jsonPath("$[1].title").isEqualTo(product2.getTitle())
                .jsonPath("$[1].description").isEqualTo(product2.getDescription());
    }

    @Test
    @DisplayName("Add Product to Cart")
    void shouldAddProductToCart(){
        Cart product = new Cart();
        product.setProductId(UUID.randomUUID().toString());
        product.setPrice(199.99);
        product.setCategoryId(5);
        product.setQuantity(66);
        product.setDescription("Test Description");
        product.setTitle("Test Product");

        when(cartServiceClient.addToCart(product))
                .thenReturn(Mono.just(product));

        client.post()
                .uri("/api/gateway/cart/addToCart")
                .body(Mono.just(product), Product.class)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.productId").isEqualTo(product.getProductId())
                .jsonPath("$.categoryId").isEqualTo(product.getCategoryId())
                .jsonPath("$.price").isEqualTo(product.getPrice())
                .jsonPath("$.quantity").isEqualTo(product.getQuantity())
                .jsonPath("$.title").isEqualTo(product.getTitle())
                .jsonPath("$.description").isEqualTo(product.getDescription());


    }


    @Test
    @DisplayName("Retrieve the Cart Items")
    void shouldGetAllProductsInTheCart(){
        Cart product = new Cart();
        product.setProductId(UUID.randomUUID().toString());
        product.setPrice(199.99);
        product.setCategoryId(5);
        product.setQuantity(66);
        product.setDescription("Test Description");
        product.setTitle("Test Product");

        Cart product1 = new Cart();
        product1.setProductId(UUID.randomUUID().toString());
        product1.setPrice(250);
        product1.setCategoryId(9);
        product1.setQuantity(31);
        product1.setDescription("Test Description for product1");
        product1.setTitle("Test Product1");

        List<Cart> productList = new ArrayList<>();
        productList.add(product);
        productList.add(product1);

        Flux<Cart> allProducts = Flux.fromIterable(productList);

        when(cartServiceClient.getTheCart())
                .thenReturn(allProducts);

        client.get()
                .uri("/api/gateway/cart")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].productId").isEqualTo(product.getProductId())
                .jsonPath("$[0].categoryId").isEqualTo(product.getCategoryId())
                .jsonPath("$[0].price").isEqualTo(product.getPrice())
                .jsonPath("$[0].quantity").isEqualTo(product.getQuantity())
                .jsonPath("$[0].title").isEqualTo(product.getTitle())
                .jsonPath("$[0].description").isEqualTo(product.getDescription())
                .jsonPath("$[1].productId").isEqualTo(product1.getProductId().toString())
                .jsonPath("$[1].categoryId").isEqualTo(product1.getCategoryId())
                .jsonPath("$[1].price").isEqualTo(product1.getPrice())
                .jsonPath("$[1].quantity").isEqualTo(product1.getQuantity())
                .jsonPath("$[1].title").isEqualTo(product1.getTitle())
                .jsonPath("$[1].description").isEqualTo(product1.getDescription());
    }


}
