package sample;

import com.sun.scenario.effect.Color4f;
import com.sun.scenario.effect.light.SpotLight;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.VPos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.fxyz.scene.paint.Palette;
import org.fxyz.shapes.composites.PolyLine3D;
import org.fxyz.shapes.primitives.SpringMesh;
import org.fxyz.shapes.primitives.Text3DMesh;
import org.fxyz.shapes.primitives.helper.Text3DHelper;
import org.fxyz.utils.CameraTransformer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main extends Application {

    public static final int WINDOW_WIDTH=1200;
    public static final int WINDOW_HEIGHT=200;
    Color textColor=Color.YELLOW;

    @Override
    public void start(Stage primaryStage) throws IOException {
        //Container
        Group root = new Group();
        //Creating 3DShape
        Text3DMesh text3DMesh = new Text3DMesh("Text sample. JavaFX");
        text3DMesh.setTranslateY(2*WINDOW_HEIGHT/3);
        //Creating Point Light
        PointLight point1 = new PointLight();
        point1.setColor(textColor);
        point1.setLayoutX(WINDOW_WIDTH/4);
        point1.setLayoutY(WINDOW_HEIGHT/2);
        point1.setTranslateZ(-1100);
        point1.getScope().add(text3DMesh);
        //Creating Point Light
        PointLight point2 = new PointLight();
        point2.setColor(textColor);
        point2.setLayoutX(3*WINDOW_WIDTH/4);
        point2.setLayoutY(WINDOW_HEIGHT/2);
        point2.setTranslateZ(-1100);
        point2.getScope().add(text3DMesh);
        //color Picker
        final ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue(textColor);
        colorPicker.setFocusTraversable(false);
        //combobox
        ObservableList<String> langs = FXCollections.observableArrayList("Light 1", "Light 2");
        ComboBox<String> langsComboBox = new ComboBox<String>(langs);
        langsComboBox.setValue("Light 1");
        langsComboBox.setLayoutX(200);
        langsComboBox.setFocusTraversable(false);
        //Adding nodes inside Container
        root.getChildren().addAll(text3DMesh,point1,colorPicker,langsComboBox);
        //Adding to scene
        Scene scene = new Scene(root,WINDOW_WIDTH,WINDOW_HEIGHT);
        //Creating Perspective View Camera
        PerspectiveCamera cam=  new PerspectiveCamera(false);
        scene.setCamera(cam);
        primaryStage.setTitle("3D World");
        primaryStage.setScene(scene);
        primaryStage.show();

        langsComboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(langsComboBox.getValue().equals("Light 1")){
                    root.getChildren().remove(point2);
                    root.getChildren().addAll(point1);
                } else if(langsComboBox.getValue().equals("Light 2")){
                    root.getChildren().remove(point1);
                    root.getChildren().addAll(point2);
                }
            }
        });

        scene.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                String ch=event.getCharacter();
                if(ch=="\r"){
                    StringBuffer buffer = new StringBuffer(text3DMesh.getText3D());
                    buffer.append("\n");
                    text3DMesh.setText3D(buffer.toString());
                }
                if(ch.length()==0){
                    if(!text3DMesh.getText3D().isEmpty()){
                        StringBuffer buffer = new StringBuffer(text3DMesh.getText3D());
                        text3DMesh.setText3D(buffer.substring(0,buffer.length()-1).toString());
                    }
                } else {
                    StringBuffer buffer = new StringBuffer(text3DMesh.getText3D());
                    buffer.append(ch);
                    text3DMesh.setText3D(buffer.toString());
                }
            }
        });
        colorPicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Color color=colorPicker.getValue();
                point1.setColor(color);
                point2.setColor(color);
            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
