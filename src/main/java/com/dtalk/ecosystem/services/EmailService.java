package com.dtalk.ecosystem.services;

public interface EmailService {

    public void confirmationSignup(String to, String subject, String text);
    public void resetPassword(String to, String text);

}
