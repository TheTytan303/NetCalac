package window.calculator.ipV4.modulesV4;

import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;


public class newWindowPing {
    private Scene returnVale;
    public TextArea pingField;

    public newWindowPing(){
        pingField = new TextArea();
        pingField.setEditable(false);
        pingField.setMaxHeight(100);
        pingField.setMaxWidth(500);
        //pingField.setText(adr.toString());
        BorderPane mainPane = new BorderPane();
        mainPane.setCenter(pingField);
        returnVale = new Scene(mainPane);
    }
    public Scene getNewWindow(){
        return  returnVale;
    }
}
