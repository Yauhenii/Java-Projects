package com.company;

public class Postgraduate extends Student {
    //fields
    private Academic supervisor;
    //constructor
    public Postgraduate(String name, String login, String email, Academic supervisor) {
        super(name, login, email);
        this.supervisor = supervisor;
    }

    //methods
    public Academic getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Academic supervisor) {
        this.supervisor = supervisor;
    }
}
