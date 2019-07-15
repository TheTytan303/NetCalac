package window.calculator.ipV4;


import javafx.scene.control.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class WindowPing implements Runnable {
    private boolean ifPing;
    private String pingCommand;
    private TextArea pingField;

    public WindowPing(String command, TextArea resultArea){
        setIfPing(true);
        setPingCommand(command);
        setPingField(resultArea);
    }

 //public TextArea getPingField() {
 //    return pingField;
 //}
    private boolean isIfPing() {
        return ifPing;
    }
   // public String getPingCommand() {
   //    return pingCommand;
   //  }
    public void setIfPing(boolean ifPing) {
        this.ifPing = ifPing;
    }
    public void setPingCommand(String pingCommand) {
        this.pingCommand = pingCommand;
    }
    public void setPingField(TextArea pingField) {
        this.pingField = pingField;
    }
    public void run() {
        while(isIfPing())
        {
            executeCommand(pingCommand, pingField);
        }
    }
    private static void executeCommand(String command, TextArea area){
        try {
            String s;
            Process process = Runtime.getRuntime().exec(command);
            //System.out.println("the output stream is ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((s = reader.readLine()) != null) {
                if (s.contains("Rep") || s.contains("Req")) {
                    System.out.println(s);
                    area.setText(area.getText() + "\n" + s);
                    area.setScrollTop(3000);                               // <--------------------------repair
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {        }
    }
}