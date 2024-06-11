package com.dtalk.ecosystem.services;

import com.dtalk.ecosystem.DTOs.request.SignUpRequest;
import com.dtalk.ecosystem.DTOs.request.SigninRequest;
import com.dtalk.ecosystem.DTOs.response.JwtAuthenticationResponse;
import com.dtalk.ecosystem.entities.User;

public interface AuthenticationService {
    User signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SigninRequest request);

    public Boolean verifCode(String code);

    public  JwtAuthenticationResponse enableUser(Long idUser);

    public JwtAuthenticationResponse disableUser(Long idUser);


}
