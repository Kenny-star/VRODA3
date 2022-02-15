package com.kenny.presentationlayer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenny.domainclientlayer.AuthServiceClient;
import com.kenny.domainclientlayer.CartServiceClient;
import com.kenny.domainclientlayer.ProductServiceClient;
import com.kenny.dtos.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;
import static org.springframework.http.HttpStatus.UNSUPPORTED_MEDIA_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = ApiGatewayController.class)
@AutoConfigureWebTestClient
class ApiGatewayControllerTests {

    private static final Set<String> ROLE_CLERK = Collections.singleton("ROLE_CLERK");
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductServiceClient productServiceClient;

    @MockBean
    private AuthServiceClient authServiceClient;
  
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
                .body(Mono.just(product), Cart.class)
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

    @Test
    @DisplayName(" Delete Cart Item")
    void shouldDeleteCartItem(){
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
                .body(Mono.just(product), Cart.class)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody();

        assertEquals(Optional.ofNullable(product.getProductId()),Optional.ofNullable(product.getProductId()));

        client.delete()
                .uri("/api/gateway/cart/delete/{product_id}", product.getProductId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody();

        assertEquals(null, cartServiceClient.getTheCart());
    }

    @Test
    @DisplayName("Update Cart Item")
    void shouldUpdateCartProduct(){
        Cart cart = new Cart();
        cart.setProductId(UUID.randomUUID().toString());
        cart.setPrice(199.99);
        cart.setCategoryId(5);
        cart.setQuantity(66);
        cart.setDescription("Test Description");
        cart.setTitle("Test Product");

        Cart cart1 = new Cart();
        cart1.setProductId(UUID.randomUUID().toString());
        cart1.setPrice(250);
        cart1.setCategoryId(9);
        cart1.setQuantity(31);
        cart1.setDescription("Test Description for product1");
        cart1.setTitle("Test Product1");

        when(cartServiceClient.addToCart(cart))
                .thenReturn(Mono.just(cart));

        client.post()
                .uri("/api/gateway/cart/addToCart")
                .body(Mono.just(cart), Cart.class)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.productId").isEqualTo(cart.getProductId())
                .jsonPath("$.categoryId").isEqualTo(cart.getCategoryId())
                .jsonPath("$.price").isEqualTo(cart.getPrice())
                .jsonPath("$.quantity").isEqualTo(cart.getQuantity())
                .jsonPath("$.title").isEqualTo(cart.getTitle())
                .jsonPath("$.description").isEqualTo(cart.getDescription());

        assertEquals(null,cartServiceClient.getTheCart());

        when(cartServiceClient.updateCart(cart1))
                .thenReturn(Mono.just(cart1));

        client.put()
                .uri("/api/gateway/cart/update/{product_id}", cart.getProductId())
                .body(Mono.just(cart1), Cart.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody();

        assertEquals(null,cartServiceClient.getTheCart());

    }

    @Test
    @DisplayName("Should successfully register a user")
    void shouldAddUserSuccessfullyThroughRegisteration(){
        UserDetailsAuth user = new UserDetailsAuth();
        user.setUsername("Donut King");
        user.setPassword("OmegaDonut");
        user.setEmail("Donut@gmail.com");
        user.setRole(ROLE_CLERK);

        Mono<ResponseEntity<String>> responseEntity = authServiceClient.signupUser(user);
        when(authServiceClient.signupUser(user))
                .thenReturn(responseEntity);

        client.post()
                .uri("/api/gateway/auth/signup")
                .body(Mono.just(user), UserDetailsAuth.class)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();

        verify(authServiceClient, times(2)).signupUser(user);
    }

    @Test
    @DisplayName("Should successfully login a user")
    void shouldAuthenticateUserSuccessfullyThroughLogin(){
        UserDetailsAuth user = new UserDetailsAuth();
        user.setUsername("Donut King");
        user.setPassword("OmegaDonut");
        user.setEmail("Donut@gmail.com");
        user.setRole(ROLE_CLERK);

        Mono<ResponseEntity<String>> responseEntity = authServiceClient.signupUser(user);
        when(authServiceClient.signupUser(user))
                .thenReturn(responseEntity);

        client.post()
                .uri("/api/gateway/auth/signup")
                .body(Mono.just(user), UserDetailsAuth.class)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();

        UserDetails userCredentials = new UserDetails();
        userCredentials.setUsername(user.getUsername());
        userCredentials.setPassword(user.getPassword());

        Mono<ResponseEntity<String>> responseEntity2 = authServiceClient.signinUser(userCredentials);
        when(authServiceClient.signinUser(userCredentials))
                .thenReturn(responseEntity2);

        client.post()
                .uri("/api/gateway/auth/signin")
                .body(Mono.just(userCredentials), UserDetails.class)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();

        verify(authServiceClient, times(2)).signinUser(userCredentials);
        verify(authServiceClient, times(2)).signupUser(user);
    }

    @Test
    @DisplayName("Should validate user refresh token")
    void shouldAuthenticateUserSuccessfullyThroughRefreshToken(){
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setRefreshToken("1860ce26-c68e-4237-8fe0-664ca64eff90");

        Mono<ResponseEntity<String>> responseEntity = authServiceClient.validateRefreshToken(refreshToken);
        when(authServiceClient.validateRefreshToken(refreshToken))
                .thenReturn(responseEntity);

        client.post()
                .uri("/api/gateway/auth/refreshToken")
                .body(Mono.just(refreshToken), RefreshToken.class)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();

        verify(authServiceClient, times(2)).validateRefreshToken(refreshToken);
    }
}
