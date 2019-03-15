package com.company;

public class Student extends Person implements Notifable {
    //fields
    private String login;
    private String email;
    //constructor

    public Student(String name, String login, String email) {
        super(name);
        this.login = login;
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void notify(String messsage) {
        System.out.println(login+" "+messsage);
    }
}
