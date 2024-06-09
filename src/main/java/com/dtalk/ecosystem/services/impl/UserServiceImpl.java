package com.dtalk.ecosystem.services.impl;

import com.dtalk.ecosystem.DTOs.request.SignUpRequest;
import com.dtalk.ecosystem.entities.Role;
import com.dtalk.ecosystem.entities.User;
import com.dtalk.ecosystem.repositories.UserRepository;
import com.dtalk.ecosystem.services.UserService;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }

    @Override
    public User getUserById(Long idUser) {
        return userRepository.findById(idUser).get();
    }

    @Override
    public List<User> retrieveAllUserByRole(String role) {
        return userRepository.findByRole(Role.valueOf(role));
    }

    @Override
    public User addUser(User user) {
        String randomCode = RandomString.make(10);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(randomCode));
        User u = userRepository.save(user);
        return u;

    }
}
