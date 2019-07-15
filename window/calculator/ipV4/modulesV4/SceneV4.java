package window.calculator.ipV4.modulesV4;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import network.calculator.Adress;
import network.calculator.Host;
import window.calculator.ipV4.topMenu.TopMenu;

import java.util.*;

public class SceneV4 {
    private Map <String, ModuleV4> modules;
    private BorderPane pane1;
    private ModuleV4 activeModule;
    private ListView<String> rightList;


    public SceneV4(){

        modules = new LinkedHashMap<>();
        pane1 = new BorderPane();
        rightList = new ListView<>();
        rightList.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            setActiveModule(t1);
            System.out.println(s);
            System.out.println(t1);
        });
        rightList.setCellFactory(lv ->{

            ListCell<String> cell = new ListCell<>();
            ContextMenu contextMenu = new ContextMenu();

            cell.setOnMouseEntered(mouseEvent -> {
                try{
                    rightList.setTooltip(modules.get(cell.getItem()).getAdressTooltip());
                }
                catch (Exception ignore){}

            });

            MenuItem editName = new MenuItem();
            editName.setText("zmien nazwe");
            editName.setOnAction(event ->{
                String target = cell.getItem();
                TextInputDialog nazwa= new TextInputDialog();
                nazwa.setTitle("Renaming module");
                nazwa.setHeaderText("i need You to put new name of module: ");
                Optional<String> name = nazwa.showAndWait();
                if(name.isPresent()) {
                    String newName = name.get();
                    modules.put(newName, new ModuleV4(modules.get(target)));
                    modules.get(newName).setName(newName);
                    modules.remove(target);
                }
                this.updateRightList();
            });
            MenuItem delete = new MenuItem();
            delete.setText("usun");
            delete.setOnAction(event ->{
                String target = cell.getItem();
                modules.remove(target);
                this.updateRightList();
            });
            MenuItem copy = new MenuItem();
            copy.setText("kopiuj");
            copy.setOnAction(event ->{
                String target = cell.getItem();
                String newName = modules.get(target).getName();
                newName += " - copy";
                modules.put(newName, new ModuleV4(modules.get(target)));
                modules.get(newName).setName(newName);
                //modules.remove(target);
                this.updateRightList();
            });
            contextMenu.getItems().addAll(editName,copy, delete);
            cell.textProperty().bind(cell.itemProperty());
            cell.setContextMenu(contextMenu);



            return cell;
        });

    }

    public BorderPane getScene() {

        this.addModule("first module");
        //this.addModule("dsss");
        //ModuleV4 s1= ;
        //this.setActiveModule("dss");
        this.setActiveModule("first module");
        pane1.setTop(TopMenu.getMenu(this));

        updateRightList();
        pane1.setRight(rightList);
        pane1.setMargin(pane1.getTop(), new Insets(0,0,10,0));
        //pane1.setMargin(pane1.getBottom(), new Insets(10,0,0,0));
        return pane1;
    }

    public void setActiveModule(String name){
        try {
            activeModule = modules.get(name);
            this.pane1.setCenter(activeModule.getLAF());
        }
        catch (NullPointerException ignore){

        }
    }
    public ModuleV4 getActiveModule(){
        return activeModule;
    }

    public void addModule(String name) throws NullPointerException{
        modules.put(name, new ModuleV4(name));
        updateRightList();
    }
    public void addModule(String name, Host host){
        modules.put(name, new ModuleV4(name, host));
        updateRightList();
    }


    public void removeCurrentModule(String name)throws NullPointerException{
        modules.remove(name);
        updateRightList();
    }
    private void updateRightList(){
        ObservableList<String> items = FXCollections.observableList(new ArrayList(modules.keySet()));

        rightList.setItems(items);
    }
    public void closeAllPings(){
        modules.forEach((k ,v) -> {
            if(v.ping.getText()=="stop pinging"){
                v.pingOperation.setIfPing(false);
            }
        });
    }
}
