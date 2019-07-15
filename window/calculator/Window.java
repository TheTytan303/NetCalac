package window.calculator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import window.calculator.ipV4.modulesV4.SceneV4;

public class Window extends Application {

    private Stage window;
    SceneV4 s;

    public void run(String[] args){
        System.out.println("Window opened!");

        launch(args);
    }
    @Override
    public void start(Stage primaryStage){

        window = primaryStage;
        window.setTitle("NET CALC v2.0");

        s = new SceneV4();
        // s.getScene().
        //return ;

        primaryStage.setScene(new Scene(s.getScene(), 500, 400));
        primaryStage.show();

        window.setOnCloseRequest((WindowEvent event)->mainWindowClosed());

       window.setMinWidth(500);
       //window.setMaxWidth(800);
       window.setMinHeight(420);
       window.setMaxHeight(450);
    }

    void mainWindowClosed(){
        s.closeAllPings();
    }
}