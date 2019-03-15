package sample;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {

    public final String REG_EXP ="((0?[1-9]|1[0-9]|2[0-9]|3[01])[.](0?[123578]|1[02])|((0?[1-9]|1[0-9]|2[0-9]|30)[.](0?[469]|11)))[.]\\d+";

    public TextArea textArea;
    public ListView listView;
    public Button button;

    public void click(ActionEvent actionEvent) {
        listView.getItems().clear();
        Pattern pattern=Pattern.compile(REG_EXP);
        Matcher matcher=pattern.matcher(textArea.getText());
        while (matcher.find()) {
            String s = matcher.group();
            listView.getItems().add(s);
        }
    }
}
