package sample;

import javafx.scene.text.Text;

public class PressedKey implements Observer {
    private String ch;
    private ConcreteObservable concreteObservable;
    Text text;

    public PressedKey(ConcreteObservable concreteObservable, Text text) {
        this.concreteObservable = concreteObservable;
        concreteObservable.registerObserver(this);
        this.text=text;
    }

    @Override
    public void update(String ch) {
        this.ch=ch;
        display();
    }

    public void display() {
        text.setText(ch);
    }
}
