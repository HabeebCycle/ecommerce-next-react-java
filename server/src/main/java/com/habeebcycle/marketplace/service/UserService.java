package com.habeebcycle.marketplace.service;

import com.habeebcycle.marketplace.model.entity.user.Role;
import com.habeebcycle.marketplace.model.entity.user.User;
import com.habeebcycle.marketplace.repository.RoleRepository;
import com.habeebcycle.marketplace.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this. userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public Optional<User> getUser(Long id){
        return userRepository.findById(id);
    }

    public Optional<User> getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public Optional<User> getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User addUser(User user){
        return userRepository.save(user);
    }

    public User updateUser(User user){
        return userRepository.save(user);
    }

    public void deleteUser(User user){
        userRepository.delete(user);
    }

    public boolean usernameExists(String username){
        return userRepository.existsByUsername(username);
    }

    public boolean emailExists(String email){
        return userRepository.existsByEmail(email);
    }

    public Optional<Role> getUserRole(Long id){
        return roleRepository.findById(id);
    }
}
