package window.calculator.ipV4.topMenu;

import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Pair;
import network.calculator.Adress;
import network.calculator.Host;
import network.calculator.Network;
import window.calculator.ipV4.WindowPing;
import window.calculator.ipV4.modulesV4.ModuleV4;
import window.calculator.ipV4.modulesV4.SceneV4;
import window.calculator.ipV4.modulesV4.newWindowPing;

public class TopMenu {
    public static MenuBar getMenu(SceneV4 sceneV4){
        MenuBar returnVale = new MenuBar();

        //---------------------------------------------------------------Application
        Menu appMenu = new Menu("Application");
        //----------------------------------------------------------Exit
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(actionEvent -> System.exit(0));
        appMenu.getItems().add(exit);
        //----------------------------------------------------------Ping in new window
        MenuItem ping = new MenuItem("Open Windowed Ping");
        ping.setOnAction(actionEvent -> newPing(sceneV4.getActiveModule()));
        appMenu.getItems().add(ping);

        //---------------------------------------------------------------File
        Menu fileMenu = new Menu("File");
        //----------------------------------------------------------To File
        MenuItem toFile = new MenuItem("To file");
        toFile.setOnAction(actionEvent -> saveToFile(sceneV4));
        fileMenu.getItems().add(toFile);
        //---------------------------------------------------------------Modules
        Menu modulesMenu = new Menu("Modules");
        //----------------------------------------------------------add module
        MenuItem addModule = new MenuItem("New module");
        addModule.setOnAction(actionEvent -> addModule(sceneV4));
        modulesMenu.getItems().add(addModule);
        //----------------------------------------------------------current module
        MenuItem showInfofModule = new MenuItem("Current module");
        showInfofModule.setOnAction(actionEvent -> showInfoOfModule(sceneV4.getActiveModule()));
        modulesMenu.getItems().add(showInfofModule);
       //----------------------------------------------------------remove current module
        MenuItem removeuCurrfModule = new MenuItem("Remove current module");
        removeuCurrfModule.setOnAction(actionEvent -> removeCurrentModule(sceneV4));
        modulesMenu.getItems().add(removeuCurrfModule);





        returnVale.getMenus().addAll(appMenu,fileMenu, modulesMenu);
        //returnVale.setPadding(new Insets(0,0,15,0));

        return returnVale;
    }
    private static void saveToFile(SceneV4 sceneV4){
        ModuleV4 m = sceneV4.getActiveModule();
        Network net = m.getActualNetwork();
        try{
            PrintWriter writer = new PrintWriter(m.getName()+".txt", StandardCharsets.UTF_8);
            String s = net.print();
            String[] tab = s.split("\\n");
            for(int i =0; i<tab.length;i++){
                writer.println(tab[i]);
            }
            writer.close();
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
    private static void addModule(SceneV4 sceneV4) {
        System.out.println("adding module");
        ButtonType createButton = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        Dialog<List<String>> nazwa= new Dialog();
        nazwa.setTitle("Creating new module");
        nazwa.getDialogPane().getButtonTypes().addAll(createButton, ButtonType.CANCEL);
        //nazwa.setHeaderText("i need You to put name of new module: ");
        GridPane windowPane = new GridPane();
        TextField nameInput = new TextField();
        TextField addressInput = new TextField();
        ChoiceBox maskInput = new ChoiceBox(FXCollections.observableArrayList("8","12","24"));
        windowPane.setHgap(10);
        windowPane.setVgap(10);
        windowPane.add(new Label("Nazwa: "),0 ,0);
        windowPane.add(nameInput,1 ,0);
        windowPane.add(new Label("Adres: "),0 ,1);
        windowPane.add(addressInput,1 ,1);
        windowPane.add(new Label("maska: "),0 ,2);
        windowPane.add(maskInput,1 ,2);
        nazwa.getDialogPane().setContent(windowPane);
        nazwa.setResultConverter(dialogButton -> {
            if(dialogButton == createButton){
                //String returnVale = "";
                //returnVale += addressInput.getText();
                //returnVale += "/";
                //returnVale +=maskInput.getValue();
                //returnVale +="|";
                //returnVale +=nameInput.getText();
                List<String> returnVale = new ArrayList();
                returnVale.add(nameInput.getText());
                returnVale.add(addressInput.getText());
                returnVale.add((String)maskInput.getValue());
                return  returnVale;
                        //new Pair<String, String>(nameInput.getText(), returnVale);
            }
            else{
                return null;
            }
        });



        Optional<List<String>> returnedList = nazwa.showAndWait();
        if(returnedList.isPresent()){
            List<String> gotList = returnedList.get();
            try{
                Host host = new Host(Host.adrStringToHost(gotList.get(1)+"/"+gotList.get(2)));
                sceneV4.addModule(gotList.get(0), host);
                sceneV4.setActiveModule(gotList.get(0));
            }
            catch (Exception ignore){}
//new Adress(),gotList.get(1)
        }
    }
    private static void showInfoOfModule(ModuleV4 m){
        System.out.println("showing module");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Module Dialog" + m.getName());
        alert.setHeaderText(null);
        String s = m.getActualNetwork().toString();
        alert.setContentText( m.getActualNetwork().print());
        alert.showAndWait();
    }
    private static void removeCurrentModule(SceneV4 m){
       m.removeCurrentModule(m.getActiveModule().getName());
    }
    private static void newPing(ModuleV4 m){

        Stage windowedPing = new Stage();
        windowedPing.setTitle(m.getName());
        newWindowPing one =new newWindowPing();

        Scene scene = one.getNewWindow();
        windowedPing.setScene(scene);
        windowedPing.show();
        WindowPing pingOps = new WindowPing("", one.pingField);
        Thread pingSolution;

         m.pingCommand = "ping "+ m.adr1.getText()+"."+ m.adr2.getText()+"."+ m.adr3.getText()+"."+m.adr4.getText()+" -n 1";
        pingOps = new WindowPing("", one.pingField);
        pingOps.setPingCommand(m.pingCommand);
         //System.out.println(service.pingCommand);
        pingOps.setPingField(one.pingField);
        pingSolution = new Thread(pingOps);
        WindowPing finalPingOps = pingOps;
        windowedPing.setOnCloseRequest((WindowEvent event)->finalPingOps.setIfPing(false));
        pingSolution.start();

        }
        //newWindowPing = primaryStage;
    }

