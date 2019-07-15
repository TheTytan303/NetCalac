package console.calculator;

import network.calculator.Global;
import network.calculator.Host;
import network.calculator.InvalidInputExpection;
import network.calculator.Network;

import java.net.SocketException;
import java.net.UnknownHostException;

public class Console {
    public static void runConsole(String[] args){
        Global.setWidth(4);
        Global.setNum(8);
        Host A;
        if(args.length>0) {
            try {
                A = Host.adrStringToHost(args[0]);
            } catch (InvalidInputExpection e){
                System.err.println(e.getMsg());
                return;
            }
        }
        else{
            try{
                A = new Host(Host.getLocalAdress(), Host.getLocalMask());
            }
            catch (UnknownHostException e){
                System.err.println("nie podałeś adresu oraz nie udało się pobrać adresu twojego interfejsu");
                return ;
            }
            catch (SocketException e){
                System.err.println("nie podałeś adresu oraz nie udało się pobrać adresu twojego interfejsu");
                return ;
            }

        }
        //Host[] tabOfHosts1 = new Host[7];
        //for(int i=0 ;i<7; i++){
        //    tabOfHosts1[i] = new Host(new Adress(new int[] {192, 168, 1, i*7}), 20);
        //}
        //System.out.println(A);
        Network pierwsza = new Network(A, "pierwsza");
        //pierwsza.addHost(tabOfHosts1);
        //pierwsza.show();
        System.out.println(pierwsza.print());
        System.out.println("zawartosc sieci ");
        pierwsza.show();

    }
}
