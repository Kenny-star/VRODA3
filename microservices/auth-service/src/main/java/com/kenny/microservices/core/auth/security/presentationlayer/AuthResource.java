package com.kenny.microservices.core.auth.security.presentationlayer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping()
public class AuthResource {

    @GetMapping(path = "hello")
    public String getUser(){
        log.info("wazgud lads");
        return "goodbye";
    }
}
