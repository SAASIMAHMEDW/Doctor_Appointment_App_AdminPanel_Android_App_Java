package com.example.daadmin;

public class doctorDataModel {

    String email,password,login_uid,status,name;

    public doctorDataModel() {
    }

    public doctorDataModel(String name,String email, String password, String login_uid,String status) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.login_uid = login_uid;
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin_uid() {
        return login_uid;
    }

    public String getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLogin_uid(String login_uid) {
        this.login_uid = login_uid;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
