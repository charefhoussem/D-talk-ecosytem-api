package com.dtalk.ecosystem.services;

import com.dtalk.ecosystem.entities.users.*;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    UserDetailsService userDetailsService();
    User getUserById(Long idUser) ;
    public List<User> retrieveAllUserByRole(String role);
    public List<Designer> retrieveAllDesigners();
    public List<Brand> retrieveAllBrands();
    public List<FashionDesigner> retrieveAllFashionDesigners();
    public List<Admin> retrieveAllAdmins();




}
