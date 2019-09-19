package sample;

import java.util.LinkedList;
import java.util.List;
import javafx.scene.input.KeyCode;


public class ConcreteObservable implements Observable {
    private List<Observer> observers;
    private String ch;

    ConcreteObservable(){
        observers=new LinkedList<>();
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers)
            observer.update(ch);
    }

    public void setCode(String ch) {
        this.ch = ch;
        notifyObservers();
    }
}
