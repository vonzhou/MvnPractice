package com.vonzhou.mvnbook.account.persist;

public interface AccountPersistService{
    /**
     * persist Account to xml
     */
    Account createAccount(Account account) throws  AccountPersistException;


    Account readAccount(String id) throws AccountPersistException;
    Account updateAccount(Account account) throws  AccountPersistException;
    void deleteAccount(Account account) throws AccountPersistException;
}