package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class SampleController {

    public final ObservableList<String> list = FXCollections.observableArrayList("N", "Z", "R", "Date","Time","Email");
    public ComboBox<String> comboBox;
    public Circle circle;
    public TextField textField;

    public String regEx;


    public final double ff=10;

    public final String N_REG_EX ="[1-9][0-9]*";
    public final String Z_REG_EX ="[+-]?\\d+";
    public final String R_REG_EX ="[+-]?(\\d+[.]?(\\d*|(\\d*[e][+-]?\\d+)))|([+-]?[.](\\d*|(\\d*[e][+-]?\\d+)))";
    public final String DATE_REG_EX ="((0?[1-9]|1[0-9]|2[0-9]|3[01])[.](0?[123578]|1[02])|((0?[1-9]|1[0-9]|2[0-9]|30)[.](0?[469]|11)))[.]\\d+";
    public final String TIME_REG_EX ="((0?[0-9])|(1[0-9])|(2[1-3]))[:]((0?[0-9])|([1-5][0-9]))";
    public final String EMAIL_REG_EX="([a-zA-Z0-9]+[._]?[a-zA-Z0-9]*)*[@]([a-zA-Z0-9]+[._]?[a-zA-Z0-9]*)*[.][a-zA-Z]+";
            //"(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    public void initialize(){
        comboBox.setItems(list);
        comboBox.setValue("N");
        regEx=N_REG_EX;

        comboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue!=null) {
                    switch (newValue) {
                        case "N":
                            regEx = N_REG_EX;
                            break;
                        case "Z":
                            regEx = Z_REG_EX;
                            break;
                        case "R":
                            regEx = R_REG_EX;
                            break;
                        case "Date":
                            regEx = DATE_REG_EX;
                            break;
                        case "Time":
                            regEx = TIME_REG_EX;
                            break;
                        case "Email":
                            regEx = EMAIL_REG_EX;
                            break;
                        default:
                            regEx = "";
                            break;
                    }
                }
            }
        });

        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue!=null) {
                    if (newValue.matches(regEx)) {
                        circle.setFill(Color.GREEN);
                    } else {
                        circle.setFill(Color.RED);
                    }
                }
            }
        });
    }
}
