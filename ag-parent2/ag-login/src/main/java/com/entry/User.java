package com.entry;

import java.io.Serializable;

public class User extends BaseEntry implements Serializable {

    private String username;
    private String password;
    private String loginname;

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", loginname='" + loginname + '\'' +
                ", createDate=" + createDate +
                ", id='" + id + '\'' +
                ", createUser='" + createUser + '\'' +
                ", modifyDate=" + modifyDate +
                ", modifyUser='" + modifyUser + '\'' +
                ", delFlag='" + delFlag + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }
}
