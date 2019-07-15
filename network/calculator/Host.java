package network.calculator;
import java.net.*;

public class Host implements Comparable<Host>{
    private int mask;
    private Adress adress;
    //-------------------------------------------------------------------------------------------Constructor
    public Host(Adress adr, int msk) {
        setMask(msk);
        setAdress(adr);
    }
    public Host(Host hst) {
        setMask(hst.getMask());
        setAdress(hst.getAdress());
    }

    //-------------------------------------------------------------------------------------------Geter
    public Adress getNetworkAdress() {
        boolean[][] networkAdressBin = new boolean[Global.getWidth()][Global.getNum()];
        int k=0;
        for(int i=0; i<Global.getWidth();i++){
            for(int j=0; j<Global.getNum();j++){
                if(k<this.mask){
                    networkAdressBin[i][j]=this.adress.getVal(i, j);
                }
                else{
                    networkAdressBin[i][j]=false;
                }
                k++;
            }
        }
        return new Adress(networkAdressBin);
    }
    public Adress getAdress() {
        return adress;
    }
    public int getMask() {
        return mask;
    }

    //-------------------------------------------------------------------------------------------Setter
    public void setMask(int msk) {
        if(msk > Global.getNum()*Global.getWidth() || msk <= 0){
            msk = Global.getNum()*Global.getWidth()-Global.getNum();
        }
        this.mask = msk;
    }
    public void setAdress(Adress adress) {
        this.adress = adress;
    }

    //-------------------------------------------------------------------------------------------Overrides
    @Override
    public String toString(){
        String s = (ifPrivate())? " | adres prywatny" : " | adres publiczny";
        return this.getAdress().toString()+"/"+ this.mask + s;
    }
    @Override
    public int compareTo(Host hst){
        return this.getAdress().compareTo(hst.getAdress());
    }
    //-------------------------------------------------------------------------------------------Static
    public static Host adrStringToHost (String value) throws InvalidInputExpection{
        String[] tabStr1= value.split("/");
        if(tabStr1.length!=2){
            throw new InvalidInputExpection("no address or subnet mask");
        }
        int[] argTab = new int[Global.getNum()];
        if( Integer.valueOf(tabStr1[1])<0 ||  Integer.valueOf(tabStr1[1])>(Global.getNum()*Global.getWidth())){
            throw new InvalidInputExpection("wrong subnet mask");
        }
        if(!Adress.isStringAddress(tabStr1[0])){
            throw new InvalidInputExpection("wrong address input");
        }
        String[] tabStr2=tabStr1[0].split("\\.");
        for(int i=0;i<tabStr2.length;i++){
            argTab[i] = Integer.valueOf(tabStr2[i]);
        }
        int mask = Integer.valueOf(tabStr1[1]);
        return new Host(new Adress(Adress.StringToAdress(tabStr1[0])),mask);
    }
    public static Adress getLocalAdress() throws UnknownHostException {
        return new Adress(Adress.StringToAdress(InetAddress.getLocalHost().getHostAddress()));
    }
    public static int getLocalMask() throws UnknownHostException, SocketException{
        InetAddress localHost = Inet4Address.getLocalHost();
        NetworkInterface networkInterface = NetworkInterface.getByInetAddress(localHost);


        return networkInterface.getInterfaceAddresses().get(0).getNetworkPrefixLength();
    }

    //-------------------------------------------------------------------------------------------public methods
    public boolean ifPrivate(){
        if(Global.getNum()!=8){
            return false;
        }
        if(Global.getWidth()!=4){
            return false;
        }
        if(this.getMask()== 8 ){
            if(this.getNetworkAdress().getAdressInt()[0]==10){
                return true;
            }
            else{
                return  false;
            }
        }
        else{
            if (this.getMask()== 12){
                if(this.getNetworkAdress().getAdressInt()[0]==172 && this.getNetworkAdress().getAdressInt()[1]>=16 && this.getNetworkAdress().getAdressInt()[2]<=31){
                    return true;
                }
                else{
                    return  false;
                }
            }
        }
        if(this.getMask()== 16){
            if(this.getNetworkAdress().getAdressInt()[0]==192 && this.getNetworkAdress().getAdressInt()[1]==168){
                return true;
            }
            else{
                return  false;
            }
        }
        return false;
    }

}