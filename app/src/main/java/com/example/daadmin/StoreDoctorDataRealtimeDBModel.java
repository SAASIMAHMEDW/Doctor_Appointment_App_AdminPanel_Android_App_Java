package com.example.daadmin;

public class StoreDoctorDataRealtimeDBModel {
    String name,email,gender,status,login_uid,profile_pic_url,phone_no;

    public StoreDoctorDataRealtimeDBModel() {
    }

    public StoreDoctorDataRealtimeDBModel(String name, String email, String gender, String status, String login_uid, String profile_pic_url,String phone_no) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.status = status;
        this.login_uid = login_uid;
        this.profile_pic_url = profile_pic_url;
        this.phone_no = phone_no;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLogin_uid() {
        return login_uid;
    }

    public void setLogin_uid(String login_uid) {
        this.login_uid = login_uid;
    }

    public String getProfile_pic_url() {
        return profile_pic_url;
    }

    public void setProfile_pic_url(String profile_pic_url) {
        this.profile_pic_url = profile_pic_url;
    }
}
