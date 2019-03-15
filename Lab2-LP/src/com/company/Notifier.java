package com.company;

import java.util.Set;

public class Notifier<T extends Notifable> {
    //fields
    private Set<T> notifiables;
    //constructor
    public Notifier(Set<T> notifiables) {
        this.notifiables = notifiables;
    }

    //method
    void doNotifyAll(String message){
        for(T t : notifiables){
            t.notify(message);
        }
    }
}
