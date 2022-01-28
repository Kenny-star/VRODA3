package com.kenny.microservices.core.auth.datalayer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository <UserEntity,Long> {

    UserEntity findByUsername(String username);
}
