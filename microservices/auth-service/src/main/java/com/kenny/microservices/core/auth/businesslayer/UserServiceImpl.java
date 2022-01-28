package com.kenny.microservices.core.auth.businesslayer;

import com.kenny.microservices.core.auth.datalayer.Role;
import com.kenny.microservices.core.auth.datalayer.RoleRepo;
import com.kenny.microservices.core.auth.datalayer.UserEntity;
import com.kenny.microservices.core.auth.datalayer.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService{
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    @Override
    public UserEntity saveUser(UserEntity user) {
        log.info("Saving new user to the database");
        return userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role to the database");
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role to user");
        UserEntity user = userRepo.findByUsername(username);
        Role role = roleRepo.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public UserEntity getUser(String username) {
        log.info("Getting user");
        return userRepo.findByUsername(username);
    }

    @Override
    public List<UserEntity> getUsers() {
        log.info("Getting users");
        return userRepo.findAll();
    }
}
