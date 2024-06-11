package com.dtalk.ecosystem.services;

public interface EmailService {

    public void confirmationSignup(String to, String subject, String text);
}
