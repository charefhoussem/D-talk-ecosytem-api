package com.dtalk.ecosystem.services;

import com.dtalk.ecosystem.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    UserDetailsService userDetailsService();
    User getUserById(Long idUser) ;
    public List<User> retrieveAllUserByRole(String role);

}
