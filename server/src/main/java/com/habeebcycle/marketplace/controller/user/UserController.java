package com.habeebcycle.marketplace.controller.user;

import com.habeebcycle.marketplace.exception.NotFoundException;
import com.habeebcycle.marketplace.model.entity.user.User;
import com.habeebcycle.marketplace.payload.user.UserIdentityAvailability;
import com.habeebcycle.marketplace.security.CurrentUser;
import com.habeebcycle.marketplace.security.UserPrincipal;
import com.habeebcycle.marketplace.service.UserService;
import com.habeebcycle.marketplace.util.ApplicationConstants;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApplicationConstants.USER_ENDPOINT)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_SUPER_USER', 'ROLE_SELLER', 'ROLE_MANAGER', 'ROLE_CUSTOMER')")
    public User getCurrentUser(@CurrentUser UserPrincipal currentUser){
        return userService.getUser(currentUser.getId()).get();
    }

    @GetMapping("/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username){
        Boolean isAvailable = !userService.usernameExists(username);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email){
        Boolean isAvailable = !userService.emailExists(email);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/{username}")
    public User getUserProfile(@PathVariable(value = "username") String username){
        return userService.getUserByUsername(username)
                .orElseThrow(() -> new NotFoundException("User", "username", username));
    }
}
