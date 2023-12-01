package com.example.daadmin;

import java.util.ArrayList;

public class adminUserModel {
    String name,age,email,password,passwordHint,gender,aboutYourSelf;
    String doctorSpeci;
    String firestore_uid;
    String login_uid;
    String adminID;
    String Status;
    String profile_url;
    String phone_no;

    public adminUserModel() {
    }


    public adminUserModel(String name, String age, String email, String password, String passwordHint, String gender, String aboutYourSelf, String doctorSpeci, String firestore_uid, String login_uid,String adminID,String Status,String profile_url,String phone_no) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
        this.passwordHint = passwordHint;
        this.gender = gender;
        this.aboutYourSelf = aboutYourSelf;
        this.doctorSpeci = doctorSpeci;
        this.firestore_uid = firestore_uid;
        this.login_uid = login_uid;
        this.adminID= adminID;
        this.Status = Status;
        this.profile_url =profile_url;
        this.phone_no =phone_no;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getProfile_url() {
        return profile_url;
    }

    public void setProfile_url(String profile_url) {
        this.profile_url = profile_url;
    }

    public String getName() {
        return name;
    }

    public String getAdminID() {
        return adminID;
    }

    public String getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordHint() {
        return passwordHint;
    }

    public String getGender() {
        return gender;
    }

    public String getAboutYourSelf() {
        return aboutYourSelf;
    }

    public String getDoctorSpeci() {
        return doctorSpeci;
    }

    public String getFirestore_uid() {
        return firestore_uid;
    }

    public String getLogin_uid() {
        return login_uid;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordHint(String passwordHint) {
        this.passwordHint = passwordHint;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAboutYourSelf(String aboutYourSelf) {
        this.aboutYourSelf = aboutYourSelf;
    }

    public void setDoctorSpeci(String doctorSpeci) {
        this.doctorSpeci = doctorSpeci;
    }

    public void setFirestore_uid(String firestore_uid) {
        this.firestore_uid = firestore_uid;
    }

    public void setLogin_uid(String login_uid) {
        this.login_uid = login_uid;
    }

    //    public adminUserModel(String name, String age, String email, String password, String passwordHint, String gender, String aboutYourSelf, String doctorSpeci, String firestore_uid,String luid) {
//        this.name = name;
//        this.age = age;
//        this.email = email;
//        this.password = password;
//        this.passwordHint = passwordHint;
//        this.gender = gender;
//        this.aboutYourSelf = aboutYourSelf;
//        this.doctorSpeci = doctorSpeci;
//        this.firestore_uid = firestore_uid;
//        login_uid = luid;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getAge() {
//        return age;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public String getPasswordHint() {
//        return passwordHint;
//    }
//
//    public String getGender() {
//        return gender;
//    }
//
//    public String getAboutYourSelf() {
//        return aboutYourSelf;
//    }
//
//    public String getDoctorSpeci() {
//        return doctorSpeci;
//    }
//
//    public String getFirestore_uid() {
//        return firestore_uid;
//    }
//
//    public String getLogin_uid() {
//        return login_uid;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setAge(String age) {
//        this.age = age;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public void setPasswordHint(String passwordHint) {
//        this.passwordHint = passwordHint;
//    }
//
//    public void setGender(String gender) {
//        this.gender = gender;
//    }
//
//    public void setAboutYourSelf(String aboutYourSelf) {
//        this.aboutYourSelf = aboutYourSelf;
//    }
//
//    public void setDoctorSpeci(String doctorSpeci) {
//        this.doctorSpeci = doctorSpeci;
//    }
//
//    public void setFirestore_uid(String firestore_uid) {
//        this.firestore_uid = firestore_uid;
//    }
//
//    public void setLogin_uid(String login_uid) {
//        this.login_uid = login_uid;
//    }
}
