package com.dtalk.ecosystem.controllers;

import com.dtalk.ecosystem.entities.User;
import com.dtalk.ecosystem.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private  final UserService userService;
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public User retrieveUser(@PathVariable("id") Long idUser) {
        User user = userService.getUserById(idUser);
        return user;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/role/{role}")
    public List<User> retrieveUserByRole(@PathVariable("role") String r) {
        List<User> users = userService.retrieveAllUserByRole(r);
        return users;
    }

}
