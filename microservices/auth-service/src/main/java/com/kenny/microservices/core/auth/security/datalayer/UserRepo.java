package com.kenny.microservices.core.auth.security.datalayer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {
    User findByUserName(String username);
}
