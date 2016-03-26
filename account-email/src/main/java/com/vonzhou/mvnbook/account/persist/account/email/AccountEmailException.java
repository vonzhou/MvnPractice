package com.vonzhou.mvnbook.account.persist.account.email;

/**
 * Created by vonzhou on 16/3/16.
 */
public class AccountEmailException  extends  Exception{
    public AccountEmailException(String s, Exception e){
        System.out.println("[*E*]" + s);
        e.printStackTrace();
    }
}
