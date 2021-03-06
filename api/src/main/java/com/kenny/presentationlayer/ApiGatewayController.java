package com.kenny.presentationlayer;

import com.kenny.domainclientlayer.AuthServiceClient;
import com.kenny.domainclientlayer.CartServiceClient;
import com.kenny.domainclientlayer.ProductServiceClient;
import com.kenny.dtos.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "*")
@RestController()
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/gateway")
public class ApiGatewayController {
    private final ProductServiceClient productServiceClient;
    private final AuthServiceClient authServiceClient;
    private final CartServiceClient cartServiceClient;

    @CrossOrigin(origins = "*")
    @PostMapping(
            value = "auth/signin",
            consumes = "application/json",
            produces = "application/json"
    )
    public Mono<ResponseEntity<String>> signinUser(@RequestBody UserDetails userDetails) {
        log.info("loging user ");
        return authServiceClient.signinUser(userDetails);

    }

    @CrossOrigin(origins = "*")
    @PostMapping(
            value = "hello",
            consumes = "application/json",
            produces = "application/json"
    )
    public Mono<ResponseEntity<String>> hello(@RequestBody UserDetails userDetails) {
        log.info("hello user ");
        return authServiceClient.hello(userDetails);

    }

    @CrossOrigin(origins = "*")
    @PostMapping(
            value = "auth/signup",
            consumes = "application/json",
            produces = "application/json"
    )
    public Mono<ResponseEntity<String>> SignupUser(@RequestBody UserDetailsAuth userDetailsAuth) {
        log.info("registering user ");
        return authServiceClient.signupUser(userDetailsAuth);

    }

    @CrossOrigin(origins = "*")
    @PostMapping(
            value = "auth/refreshToken",
            consumes = "application/json",
            produces = "application/json"
    )
    public Mono<ResponseEntity<String>> ValidateRefreshToken(@RequestBody RefreshToken refreshToken) {
        log.info("validating refresh token ");
        return authServiceClient.validateRefreshToken(refreshToken);

    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "role/user")
    public Mono<ResponseEntity<String>> getUserBoard(@RequestHeader String Authorization) {
        log.info("getting user pre-authorized board ");
        return authServiceClient.getUserBoard(Authorization);

    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "role/clerk")
    public Mono<ResponseEntity<String>> getClerkBoard(@RequestHeader String Authorization) {
        log.info("getting clerk pre-authorized board ");
        return authServiceClient.getClerkBoard(Authorization);

    }
    @CrossOrigin(origins = "*")
    @GetMapping(value = "role/admin")
    public Mono<ResponseEntity<String>> getAdminBoard(@RequestHeader String Authorization) {
        log.info("getting admin pre-authorized board ");
        return authServiceClient.getAdminBoard(Authorization);

    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "role/public")
    public Mono<ResponseEntity<String>> getPublicBoard() {
        log.info("getting public board ");
        return authServiceClient.getPublicBoard();

    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "products")
    public Flux<Product> getAllProduct() {
        log.info("Getting products ");
        return productServiceClient.getAllProducts();
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "users")
    public Flux<User> getAllUser() {
        log.info("Getting products ");
        return authServiceClient.getAllUsers();
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "products/{product_id}",
                produces = "application/json")
    public Mono<Product> getProductByID(final @PathVariable String product_id){
        log.info("Getting product by productid: {}", product_id);
        return productServiceClient.getProductByID(product_id);
    }

    @CrossOrigin(origins = "*")
    @PostMapping(
            value = "newProduct",
            consumes = "application/json",
            produces = "application/json"
    )
    Mono<Product> createProduct(@RequestBody Product product) {
        return productServiceClient.createProduct(product);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping (value = "products/{product_id}")
    public Mono<Void> deleteProductById(final @PathVariable String product_id){
        return productServiceClient.deleteProduct(product_id);
    }

    @CrossOrigin(origins = "*")
    @PutMapping(
            value = "products/{product_id}",
            consumes = "application/json",
            produces = "application/json"
    )
    public Mono<Product> updateProduct(@PathVariable String product_id, @RequestBody Product product){
        product.setProductId(product_id);
        return productServiceClient.updateProduct( product);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/products/title/{title}")
    public Flux<Product> getProductByTitle(final @PathVariable String title){
        return productServiceClient.getProductByTitle(title);
    }

    @CrossOrigin(origins = "*")
    @PostMapping(
            value = "/cart/addToCart",
            consumes = "application/json",
            produces = "application/json"
    )
    public Mono<Cart> addToCart(@RequestBody Cart cart) {
        return cartServiceClient.addToCart(cart);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "cart")
    public Flux<Cart> getTheCart() {
        log.info("Getting products ");
        return cartServiceClient.getTheCart();
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping (value = "/cart/delete/{product_id}")
    public Mono<Void> deleteCart(final @PathVariable String product_id){
        return cartServiceClient.deleteCart(product_id);
    }

    @CrossOrigin(origins = "*")
    @PutMapping(
            value = "/cart/update/{product_id}",
            consumes = "application/json",
            produces = "application/json"
    )
    public Mono<Cart> updateCart(@PathVariable String product_id, @RequestBody Cart cart){
        cart.setProductId(product_id);
        log.info("Getting products - Controller");
        return cartServiceClient.updateCart( cart);
    }

}
