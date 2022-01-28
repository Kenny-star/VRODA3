package com.kenny.microservices.core.auth.businesslayer;

import com.kenny.microservices.core.auth.datalayer.Role;
import com.kenny.microservices.core.auth.datalayer.UserEntity;

import java.util.List;

public interface UserService {
    UserEntity saveUser(UserEntity user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    UserEntity getUser(String username);
    List<UserEntity>getUsers();
}
