package com.dtalk.ecosystem.services;

import com.dtalk.ecosystem.DTOs.request.authentication.ChangePasswordRequest;
import com.dtalk.ecosystem.DTOs.request.authentication.SignUpRequest;
import com.dtalk.ecosystem.DTOs.request.authentication.SigninRequest;
import com.dtalk.ecosystem.DTOs.response.JwtAuthenticationResponse;
import com.dtalk.ecosystem.entities.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AuthenticationService {
    User signup(SignUpRequest request, MultipartFile imageFile)throws IOException;

    JwtAuthenticationResponse signin(SigninRequest request);

    public Boolean verifCode(String code);

    public  JwtAuthenticationResponse enableUser(Long idUser);

    public JwtAuthenticationResponse disableUser(Long idUser);

    public void initiatePasswordReset(String email);
    public boolean validatePasswordResetToken(String token);
    public void resetPassword(String token, String newPassword);

    public void changePassword(Long idUser, ChangePasswordRequest req);


}
