package com.example.daadmin;

public class list_request_card_model {
    String name,email,problem;

    public list_request_card_model(String name,String email, String problem) {
        this.name = name;
        this.problem = problem;
        this.email = email;
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

}
