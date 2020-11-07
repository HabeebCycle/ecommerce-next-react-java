package com.habeebcycle.marketplace.controller.admin;

import com.habeebcycle.marketplace.exception.ApplicationException;
import com.habeebcycle.marketplace.exception.NotFoundException;
import com.habeebcycle.marketplace.model.entity.user.Role;
import com.habeebcycle.marketplace.model.entity.user.User;
import com.habeebcycle.marketplace.payload.APIResponse;
import com.habeebcycle.marketplace.payload.user.UserRegisterRequest;
import com.habeebcycle.marketplace.payload.user.UserUpdateRequest;
import com.habeebcycle.marketplace.service.UserService;
import com.habeebcycle.marketplace.util.ApplicationConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping(ApplicationConstants.ADMIN_ENDPOINT)
public class AdminController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AdminController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/users")
    public List<User> getAllUser(){
        return userService.getAllUser();
    }

    @GetMapping("/users/{id}")
    public User getUserId(@PathVariable Long id){
        return userService.getUser(id)
                .orElseThrow(() -> new NotFoundException("User", "id", Long.toString(id)));
    }

    @PostMapping("/users")
    public ResponseEntity<?> insertUser(@Valid @RequestBody UserRegisterRequest userRegisterRequest){
        if(userService.usernameExists(userRegisterRequest.getUsername())){
            return new ResponseEntity<>(new APIResponse(false, "Username is already taken!",
                    HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }

        if(userService.emailExists(userRegisterRequest.getEmail())){
            return new ResponseEntity<>(new APIResponse(false, "Email Address already in use!",
                    HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }

        User user = new User(userRegisterRequest.getName(), userRegisterRequest.getUsername(),
                userRegisterRequest.getEmail(), userRegisterRequest.getPassword());


        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role role = userService.getUserRole(userRegisterRequest.getRole())
                .orElseThrow(() -> new ApplicationException("User Role not set."));
        user.setRoles(Collections.singleton(role));
        user.setEnabled(true);

        user = userService.addUser(user);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> insertUser(@Valid @RequestBody UserUpdateRequest userUpdateRequest,
                                        @PathVariable Long id){
        User user = userService.getUser(id)
                .orElseThrow(() -> new NotFoundException("User", "id", Long.toString(id)));
        if(!user.getEmail().equals(userUpdateRequest.getEmail())){
            if(userService.emailExists(userUpdateRequest.getEmail())){
                return new ResponseEntity<>(new APIResponse(false, "Email Address already in use!",
                        HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
            }
        }

        user.setEmail(userUpdateRequest.getEmail());
        user.setPassword(userUpdateRequest.getPassword());
        user.setName(userUpdateRequest.getName());
        user.setUserDetails(userUpdateRequest.getUserDetails());

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        User user = userService.getUser(id)
                .orElseThrow(() -> new NotFoundException("User", "id", Long.toString(id)));
        userService.deleteUser(user);
        return new ResponseEntity<>(new APIResponse(true, "Account Deleted Successfully",
                HttpStatus.OK), HttpStatus.OK);
    }

}
