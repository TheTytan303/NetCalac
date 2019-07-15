package window.calculator;

import javafx.scene.Scene;
import window.calculator.ipV4.modulesV4.SceneV4;

public class MainScene {
    static Scene getScene(){
        SceneV4 s = new SceneV4();
       // s.getScene().
        return new Scene(s.getScene(), 400, 250);
    }
}
