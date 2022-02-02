package com.kenny.microservices.core.auth.presentationlayer;

import com.kenny.microservices.core.auth.businesslayer.UserService;
import com.kenny.microservices.core.auth.datalayer.UserDetails;
import com.kenny.microservices.core.auth.datalayer.UserEntity;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@Slf4j
@Timed("kenny.auth")
@RequiredArgsConstructor
public class AuthResource {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserEntity>>getUsers(){
        return ResponseEntity.ok().body(userService.getUsers());
    }

//    @CrossOrigin(origins = "*")
//    @PostMapping("registration")
//    //@ResponseStatus(HttpStatus.OK)
//    public UserDetails create(@Valid @RequestBody UserDetails product) {
//        log.info("hello");
//        UserDetails u = new UserDetails();
//        u.setFirstName(product.getFirstName());
//        u.setLastName(product.getLastName());
//        u.setEmail(product.getEmail());
//        u.setPassword(product.getPassword());
//        return u;
//
//    }
@CrossOrigin(origins = "*")
@PostMapping("registration")
//@ResponseStatus(HttpStatus.OK)
public ResponseEntity<?> create(@Valid @RequestBody UserDetails product) {
    log.info("hello");
    UserDetails u = new UserDetails();
    u.setFirstName(product.getFirstName());
    u.setLastName(product.getLastName());
    u.setEmail(product.getEmail());
    u.setPassword(product.getPassword());
    log.info(u.getFirstName() + u.getLastName() + u.getEmail() + u.getPassword());
    return ResponseEntity.ok().body("Something");

}
}
