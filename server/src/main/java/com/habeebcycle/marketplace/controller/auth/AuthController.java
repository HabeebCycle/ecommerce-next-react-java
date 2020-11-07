package com.habeebcycle.marketplace.controller.auth;

import com.habeebcycle.marketplace.exception.ApplicationException;
import com.habeebcycle.marketplace.model.entity.user.Role;
import com.habeebcycle.marketplace.model.entity.user.User;
import com.habeebcycle.marketplace.payload.APIResponse;
import com.habeebcycle.marketplace.payload.auth.JwtAuthenticationResponse;
import com.habeebcycle.marketplace.payload.auth.LoginRequest;
import com.habeebcycle.marketplace.payload.auth.SignUpRequest;
import com.habeebcycle.marketplace.security.jwt.JwtTokenProvider;
import com.habeebcycle.marketplace.service.UserService;
import com.habeebcycle.marketplace.util.ApplicationConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;

@RestController
@RequestMapping(ApplicationConstants.AUTH_ENDPOINT)
public class AuthController {

    private  final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(AuthenticationManager authenticationManager, UserService userService,
                          PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, "Bearer"));
        //return new ResponseEntity<>(new JwtAuthenticationResponse(jwt, "Bearer"), HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest){
        if(userService.usernameExists(signUpRequest.getUsername())){
            return new ResponseEntity<>(new APIResponse(false, "Username is already taken!",
                    HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
            //return ResponseEntity.badRequest().body(new ApiResponse(false, "Username is already taken!"));
        }

        if(userService.emailExists(signUpRequest.getEmail())){
            return new ResponseEntity<>(new APIResponse(false, "Email Address already in use!",
                    HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
            //return ResponseEntity.badRequest().body(new ApiResponse(false, "Email Address already in use!""));
        }

        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role role = userService.getUserRole(signUpRequest.getRole())
                .orElseThrow(() -> new ApplicationException("User Role not set."));
        user.setRoles(Collections.singleton(role));
        user.setEnabled(true);

        User newUser = userService.addUser(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/v1/users/{username}")
                .buildAndExpand(newUser.getUsername()).toUri();
        return ResponseEntity.created(location).body(new APIResponse(true, "User registered successfully",
                HttpStatus.CREATED));
    }
}
