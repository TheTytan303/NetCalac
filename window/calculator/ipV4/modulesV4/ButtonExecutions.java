package window.calculator.ipV4.modulesV4;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Random;

import network.calculator.Adress;
import network.calculator.Host;
import network.calculator.InvalidInputExpection;
import network.calculator.Network;
import window.calculator.ipV4.WindowPing;


public class ButtonExecutions implements EventHandler<ActionEvent> {

    private ModuleV4 service;
    ButtonExecutions(ModuleV4 i){
        service = i;
    }


    @Override
    public void handle(ActionEvent actionEvent) {
        if(actionEvent.getSource()==service.eva){
            eva();
        }
        if(actionEvent.getSource()==service.local){
            Adress tmp;
            int mask;
            try{
                tmp  = Host.getLocalAdress();
                mask =Host.getLocalMask();
            }
            catch (UnknownHostException | SocketException e){
                System.err.println("no local host received");
                service.decimals.setText(e.getMessage());
                service.binars.setText(e.getMessage());
                return;
            }
            int[] tab = tmp.getAdressInt();
            typeInto(tab, mask);
            //eva();
        }
        if(actionEvent.getSource()==service.random){
            int[] tab = new int[4];
            int mask;
            Random generator = new Random();
            for(int i=0; i<4;i++){
                tab[i] = generator.nextInt()%254;
                if(tab[i]<0){
                    tab[i] *= (-1);
                }
            }
            mask = generator.nextInt()%30;
            if(mask<0){
                mask *= (-1);
            }
            if(mask == 0){
                mask = 24;
            }
            typeInto(tab, mask);
        }
        if(actionEvent.getSource()== service.ping){
            if(service.ping.getText().compareTo("try to ping")==0) {
                service.pingCommand = "ping "+ service.adr1.getText()+"."+ service.adr2.getText()+"."+ service.adr3.getText()+"."+service.adr4.getText()+" -n 1";
                service.pingOperation = new WindowPing(service.pingCommand, service.pingField);
                service.pingOperation.setIfPing(true);
                service.ping.setText("...");
                service.ping.setText("stop pinging");
                service.pingOperation.setPingCommand(service.pingCommand);
                System.out.println(service.pingCommand);
                service.pingOperation.setPingField(service.pingField);
                service.pingSolution = new Thread(service.pingOperation);
                service.pingSolution.start();
            }
            else {
                service.pingOperation.setIfPing(false);
                service.pingField.setText("");
                service.ping.setText("try to ping");
            }
        }
    }

    private void typeInto(int[] tab, int mask){
        service.adr1.setText(tab[0]+"");
        service.adr2.setText(tab[1]+"");
        service.adr3.setText(tab[2]+"");
        service.adr4.setText(tab[3]+"");
        this.service.mask.setText(mask+"");
    }

    private void eva(){
        String tmp="";
        tmp += service.adr1.getText()+".";
        tmp += service.adr2.getText()+".";
        tmp += service.adr3.getText()+".";
        tmp += service.adr4.getText()+"/";
        tmp += service.mask.getText();


        // tmp = "";
        //tmp = mask.getText();
        Host host;
        try{
            host = new Host(Host.adrStringToHost(tmp));
            //service.actuaAdrr = host.getAdress();
            service.actuaAdrr = host.getAdress();
            service.updateTooltip();
        }catch(InvalidInputExpection e){
            service.decimals.setText(e.getMsg());
            service.binars.setText(e.getMsg());
            return;
        }

        Network net = new Network(host, service.getName());
        service.actual = net;
        tmp = "";
        tmp += Adress.adrIntToString(host.getAdress().getAdressInt())+"\n";
        tmp += Adress.adrIntToString(net.getMaskInt())+"\n";
        tmp += Adress.adrIntToString(net.getAdress().getAdressInt())+"\n";
        tmp += Adress.adrIntToString(net.getBroadcast().getAdressInt())+"\n";
        tmp += Adress.adrIntToString(net.getFirstHost().getAdressInt())+"\n";
        tmp += Adress.adrIntToString(net.getLastHost().getAdressInt());

        service.decimals.setText(tmp);

        tmp = "";
        tmp += Adress.adrBinToString(host.getAdress().getAdressBin())+"\n";
        tmp += Adress.adrBinToString(net.getMaskBin())+"\n";
        tmp += Adress.adrBinToString(net.getAdress().getAdressBin())+"\n";
        tmp += Adress.adrBinToString(net.getBroadcast().getAdressBin())+"\n";
        tmp += Adress.adrBinToString(net.getFirstHost().getAdressBin())+"\n";
        tmp += Adress.adrBinToString(net.getLastHost().getAdressBin());

        service.binars.setText(tmp);

    }
}
