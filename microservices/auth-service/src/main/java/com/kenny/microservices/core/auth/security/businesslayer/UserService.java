package com.kenny.microservices.core.auth.security.businesslayer;

import com.kenny.microservices.core.auth.security.datalayer.Role;
import com.kenny.microservices.core.auth.security.datalayer.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    User getUser(String username);
    List<User> getUsers();
}
