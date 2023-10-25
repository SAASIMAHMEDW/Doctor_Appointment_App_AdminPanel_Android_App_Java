package com.example.daadmin;

public class list_request_card_model {
    String name,problem;

    public list_request_card_model(String name, String problem) {
        this.name = name;
        this.problem = problem;
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
}
