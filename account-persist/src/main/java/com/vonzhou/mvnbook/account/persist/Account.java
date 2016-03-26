package com.vonzhou.mvnbook.account.persist;

public class Account{
    private String id;
    private String name;
    private String email;
    private String password;
    private boolean activted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActivted() {
        return activted;
    }

    public void setActivted(boolean activted) {
        this.activted = activted;
    }
}