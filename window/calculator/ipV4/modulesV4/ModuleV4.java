package window.calculator.ipV4.modulesV4;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import network.calculator.Adress;
import network.calculator.Host;
import network.calculator.Network;
import window.calculator.ipV4.WindowPing;

public class ModuleV4 {
    Button eva, local, random, ping;
    public TextField adr1;
    public TextField adr2;
    public TextField adr3;
    public TextField adr4;
    TextField mask;
    TextArea binars, decimals, pingField;
    private String name;
    Network actual;
    Adress actuaAdrr;
    public String pingCommand;
    WindowPing pingOperation;
    Thread pingSolution;
    private HBox rightMenu;
    private GridPane v4layout;
    private Tooltip adressTooltip;

    ModuleV4(String name){
        ButtonExecutions orders = new ButtonExecutions(this);
        setName(name);
        eva = new Button("Eva!");
        eva.setMinWidth(90);
        eva.setOnAction(orders);
        local = new Button("Get Local");
        local.setMinWidth(90);
        local.setOnAction(orders);
        random = new Button("random");
        random.setMinWidth(90);
        random.setOnAction(orders);
        ping = new Button("try to ping");
        ping.setMinWidth(90);
        ping.setOnAction(orders);
        //ping.setPadding(new Insets(10,0,0,0));

        pingField = new TextArea();
        pingField.setEditable(false);
        pingField.setMaxHeight(100);
        pingField.setMaxWidth(500);

        decimals =new TextArea();
        decimals.setEditable(false);
        decimals.setMinWidth(150);
        decimals.setMinHeight(110);
        decimals.setMaxWidth(150);
        decimals.setMaxHeight(110);

        binars =new TextArea();
        binars.setMaxWidth(100);
        binars.setMaxHeight(110);
        binars.setMinWidth(250);
        binars.setMinHeight(110);
        binars.setEditable(false);

        adr1 = new TextField(); adr1.setText("192");
        adr2 = new TextField(); adr2.setText("168");
        adr3 = new TextField(); adr3.setText("12");
        adr4 = new TextField(); adr4.setText("156");
        mask = new TextField(); mask.setText("24");
        mask.setMaxWidth(30);

        adr1.setMaxWidth(35);
        adr2.setMaxWidth(35);
        adr3.setMaxWidth(35);
        adr4.setMaxWidth(35);

        actuaAdrr = new Adress(new int[] {192,168,12,156});

        actual = new Network(new Host(new Adress(new int[] {192,168,12,156}), 24), this.getName());

        adressTooltip =new Tooltip();
        this.updateTooltip();


        Label decTitle = new Label("Decimal: ");
        Label binTitle = new Label("Binary: ");
        Label rightLabels = new Label("      ADDRESS: \n" +
                "         MASK: \n" +
                " NET. ADDRESS: \n" +
                "    BROADCAST: \n" +
                "   FIRST HOST: \n" +
                "    LAST HOST: ");
        rightLabels.setMinWidth(90);
        //BorderPane leftLabels = new BorderPane();

        v4layout = new GridPane();
        //v4layout.setGridLinesVisible(true);

        HBox inputMenu = new HBox();



        inputMenu.getChildren().add(new Label(""));
        inputMenu.getChildren().add(new Label(""));
        inputMenu.getChildren().add(new Label(""));
        inputMenu.getChildren().add( adr1);
        inputMenu.getChildren().add( new Label(" . "));
        inputMenu.getChildren().add( adr2);
        inputMenu.getChildren().add( new Label(" . "));
        inputMenu.getChildren().add( adr3);
        inputMenu.getChildren().add( new Label(" . "));
        inputMenu.getChildren().add( adr4);
        inputMenu.getChildren().add( new Label(" /"));
        inputMenu.getChildren().add( mask);


        v4layout.add(rightLabels, 0, 3 , 1, 1);
        v4layout.add(inputMenu,1,0,10,1);
        v4layout.add(decTitle,2,2 ,4,1);
        v4layout.add(binTitle, 6, 2,4,1);

        v4layout.add(decimals,2,3,4,1);
        v4layout.add(binars,6,3,4,1);
        //v4layout.add(eva,11,3,4,1);
        //v4layout.addColumn(10);

        //v4layout.setGridLinesVisible(true);

        rightMenu = new HBox();
        rightMenu.getChildren().add(eva);
        rightMenu.getChildren().add(local);
        rightMenu.getChildren().add(random);
        rightMenu.getChildren().add(ping);
        rightMenu.setSpacing(10);
        rightMenu.setPadding(new Insets(10, 10,10,10));

        //v4layout.setHgap(10);
        v4layout.setVgap(10);

        //mainPane.setPadding(new Insets(10 , 10 , 10 , 10));
        // mainPane.set


    }

    ModuleV4(ModuleV4 m){
        eva = m.eva;
        local = m.local;
        random = m.random;
        ping = m.ping;
        adr1 = m.adr1;
        adr2 = m.adr2;
        adr3 = m.adr3;
        adr4 = m.adr4;
        mask = m.mask;
        binars = m.binars;
        decimals = m.decimals;
        pingField = m.pingField;
        name = m.name;
        actual = m.actual;
        actuaAdrr = m.actuaAdrr;
        pingCommand = m.pingCommand;
        pingOperation = m.pingOperation;
        pingSolution = m.pingSolution;
        rightMenu = m.rightMenu;
        v4layout = m.v4layout;
        adressTooltip=m.adressTooltip;
    }

    ModuleV4(String name, Host hst){
        this(name);
        this.actuaAdrr = hst.getAdress();
        int[] tab = hst.getAdress().getAdressInt();
        String tmp;
        this.actual = new Network(hst, name);
        tmp = ""+ tab[0];
        this.adr1.setText(tmp);
        tmp = ""+ tab[1];
        this.adr2.setText(tmp);
        tmp = ""+ tab[2];
        this.adr3.setText(tmp);
        tmp = ""+ tab[3];
        this.adr4.setText(tmp);
        tmp = "" + hst.getMask();
        this.mask.setText(tmp);

    }

    BorderPane getLAF(){
        BorderPane mainPane = new BorderPane();
        mainPane.setCenter(v4layout);
        mainPane.setTop(rightMenu);
        mainPane.setBottom(pingField);
        mainPane.setMargin(mainPane.getBottom(), new Insets(10 ,0 ,0 ,0));
        mainPane.setPadding(new Insets(0,0,20,0));
        return mainPane;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return this.getName();
    }

    public String getDecimalsText(){
        return this.decimals.getText();
    }
    public String getBinrasText(){
        return this.binars.getText();
    }
    public Network getActualNetwork(){
        return actual;
    }
    public Adress getActualAdress(){
        return actuaAdrr;
    }
    public Tooltip getAdressTooltip(){
        adressTooltip.setText(this.getActualAdress().toString());
        return adressTooltip;}
    public void updateTooltip(){
        adressTooltip.setText(this.getActualAdress().toString());
    }
}
