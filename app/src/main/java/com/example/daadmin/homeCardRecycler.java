package com.example.daadmin;

public class homeCardRecycler{
    String name,problem;

    public homeCardRecycler(String name, String problem) {
        this.name = name;
        this.problem = problem;
    }

    public String getName() {
        return name;
    }

    public String getProblem() {
        return problem;
    }
}
