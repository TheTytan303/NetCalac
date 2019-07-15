import console.calculator.Console;
import network.calculator.*;
import window.calculator.Window;

import java.lang.*;

public class Main {

    public static void main(String[] args){
        //System.out.println("Hello World!");

        Global.setConsoleMode(true);

        if(Global.isConsoleMode()){
            Console.runConsole(args);
        }
        else{
            Window window = new Window();
            window.run(args);
        }


    }
}
