package com.dtalk.ecosystem.services;

import com.dtalk.ecosystem.DTOs.request.authentication.*;
import com.dtalk.ecosystem.DTOs.response.JwtAuthenticationResponse;
import com.dtalk.ecosystem.entities.users.Admin;
import com.dtalk.ecosystem.entities.users.Brand;
import com.dtalk.ecosystem.entities.users.Designer;
import com.dtalk.ecosystem.entities.users.FashionDesigner;

import java.io.IOException;

public interface AuthenticationService {
    Designer signupDesigner(SignUpDesignerAndFashionRequest request)throws IOException;
    FashionDesigner signupFashionDesigner(SignUpDesignerAndFashionRequest request)throws IOException;

    Brand signupBrand(SignUpBrandRequest request)throws IOException;

    Admin signupAdmin(SignUpAdminRequest request)throws IOException;
    JwtAuthenticationResponse signin(SigninRequest request);

    public Boolean verifCode(String code);

    public  JwtAuthenticationResponse enableUser(Long idUser);

    public JwtAuthenticationResponse disableUser(Long idUser);

    public void initiatePasswordReset(String email);
    public boolean validatePasswordResetToken(String token);
    public void resetPassword(String token, String newPassword);

    public void changePassword(Long idUser, ChangePasswordRequest req);


}
