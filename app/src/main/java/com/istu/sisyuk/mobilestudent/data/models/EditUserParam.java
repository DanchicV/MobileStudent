package com.istu.sisyuk.mobilestudent.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EditUserParam {

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("login")
    @Expose
    private String login;
    @SerializedName("password")
    @Expose
    private String password;

    public EditUserParam(String email, String login, String password) {
        this.email = email;
        this.login = login;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "{" +
                "email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
