package com.example.daadmin;

public class RequestingListCardRecyclerViewModel {

    String name,email,problem,status,doc_email;

    public RequestingListCardRecyclerViewModel() {
    }

    public RequestingListCardRecyclerViewModel(String name, String email, String problem, String status,String doc_email) {
        this.name = name;
        this.email = email;
        this.problem = problem;
        this.status = status;
        this.doc_email = doc_email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDoc_email() {
        return doc_email;
    }

    public void setDoc_email(String doc_email) {
        this.doc_email = doc_email;
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
}
