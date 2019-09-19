package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        Text log=new Text();
        log.setY(180);
        Text pr=new Text();
        pr.setY(90);
        pr.setFont(Font.font ("Verdana", 100));

        ConcreteObservable concreteObservable =new ConcreteObservable();
        PressedKey pressedKey=new PressedKey(concreteObservable,pr);
        KeyLog keyLog=new KeyLog(concreteObservable,log);

        BorderPane root = new BorderPane();
        root.getChildren().addAll(log,pr);
        Scene scene = new Scene(root, 350, 200);

        stage.setTitle("JavaFX");
        stage.setScene(scene);
        stage.show();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.isAltDown()){
                    concreteObservable.setCode("Alt");
                } else if(event.isControlDown()){
                    concreteObservable.setCode("Control");
                } else if(event.isShiftDown()){
                    concreteObservable.setCode("Shift");
                } else if(event.isMetaDown()){
                    concreteObservable.setCode("Meta");
                }
            }
        });
        scene.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                    concreteObservable.setCode(event.getCharacter());
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
