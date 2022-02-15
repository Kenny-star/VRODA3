package com.kenny.microservices.core.auth.businesslayer;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/role")
public class TestController {
    @GetMapping("/public")
    public String allAccess() {
        return "Public Content.";
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('CLERK') or hasRole('ADMIN')")
    public String userAccess() {
        return "User Content.";
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/clerk")
    @PreAuthorize("hasRole('CLERK')")
    public String moderatorAccess() {
        return "Clerk Board.";
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }
}