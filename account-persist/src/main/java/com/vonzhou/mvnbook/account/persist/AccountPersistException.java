package com.vonzhou.mvnbook.account.persist;

public class AccountPersistException extends Exception{
    public AccountPersistException(String s, Exception e){
        System.out.println("[*E*]" + s);
        e.printStackTrace();
    }
}