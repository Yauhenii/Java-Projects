package com.company;

public class Undergraduate extends Student {
    //fields
    private Academic tutor;
    //constructor
    public Undergraduate(String name, String login, String email, Academic tutor) {
        super(name, login, email);
        this.tutor = tutor;
    }

    //methods
    public Academic getTutor() {
        return tutor;
    }

    public void setTutor(Academic tutor) {
        this.tutor = tutor;
    }
}
