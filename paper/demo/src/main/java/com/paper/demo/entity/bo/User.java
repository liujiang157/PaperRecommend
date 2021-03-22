package com.paper.demo.entity.bo;

import com.paper.demo.util.MD5Util;


/**
 * @author liujiang
 * @descrpition
 * @date 2021-03-19
 */


public class User {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int isActived;

    public User(String firstName, String lastName, String password, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = MD5Util.string2MD5(password);
        this.email = email;
        this.isActived = 0;
    }

    public User() {

    }

    public User(Integer userId, String firstName, String lastName, String email, String password, int isActived) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.isActived = isActived;
    }


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
        this.password = MD5Util.string2MD5(password);
    }
}
