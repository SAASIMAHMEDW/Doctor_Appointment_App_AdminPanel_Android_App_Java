package com.example.daadmin;

public class DoctorRejectRequestModel {

    String name, email, problem, status,day,date,month,year,hour,minutes,second;

    public DoctorRejectRequestModel() {
    }

    public DoctorRejectRequestModel(String name, String email, String problem, String status,
                                    String day, String date, String month, String year,
                                    String hour, String minutes, String second) {
        this.name = name;
        this.email = email;
        this.problem = problem;
        this.status = status;
        this.day = day;
        this.date = date;
        this.month = month;
        this.year = year;
        this.hour = hour;
        this.minutes = minutes;
        this.second = second;
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

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }
}

