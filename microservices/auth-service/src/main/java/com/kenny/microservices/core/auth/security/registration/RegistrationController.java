package com.kenny.microservices.core.auth.security.registration;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping()
@AllArgsConstructor
public class RegistrationController {

    private RegistrationService registrationService;

    @PostMapping("registration")
    public String register(@RequestBody RegistrationRequest request){
        log.info("Endppoint");
        return registrationService.register(request);
    }
}
