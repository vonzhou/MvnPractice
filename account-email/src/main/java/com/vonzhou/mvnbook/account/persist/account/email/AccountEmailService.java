package com.vonzhou.mvnbook.account.persist.account.email;


public interface AccountEmailService{
    public void sendEmail(String to, String subject, String htmlText) throws  AccountEmailException;
}