package com.example.daadmin;

public class homeCardRecycler{
    String name,problem,email,status,doctor_email;

    public homeCardRecycler() {
    }

    public homeCardRecycler(String email, String name, String problem, String status,String doctor_email) {
        this.name = name;
        this.problem = problem;
        this.email = email;
        this.status = status;

    }


    public homeCardRecycler(String name, String problem) {
        this.name = name;
        this.problem = problem;
    }

    public homeCardRecycler(String name, String problem, String email,String doctor_email) {
        this.name = name;
        this.problem = problem;
        this.email = email;
        this.doctor_email = doctor_email;
    }

    public String getDoctor_email() {
        return doctor_email;
    }

    public void setDoctor_email(String doctor_email) {
        this.doctor_email = doctor_email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
