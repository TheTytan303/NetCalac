package network.calculator;
import java.util.*;

public class Network {
    private String name;
    private Adress adress;
    private int mask;
    private Set<Host> hosts;
    //-------------------------------------------------------------------------------------------Constructors
    public Network(Host hst, String nam){
        hosts = new TreeSet<>();
        hosts.add(hst);
        setName(nam);
        setMask(hst.getMask());
        setAdress(hst.getNetworkAdress());

    }
    //-------------------------------------------------------------------------------------------Setter
    public void setMask(int mask) {
        this.mask = mask;
    }
    public void sethosts(Set<Host> hosts) {
        this.hosts = hosts;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAdress(Adress adr) {
        this.adress = adr;
    }

    //-------------------------------------------------------------------------------------------Getter
    public String getName() {
        return name;
    }
    public int getMask() {
        return mask;
    }
    public Adress getAdress() {
        return adress;
    }
    public char getKlass(){
        char klasa = 'N';
        if(this.getMask()==8){
            if(this.getAdress().getAdressInt()[0]>0&&this.getAdress().getAdressInt()[0]<127){
                klasa = 'A';
            }
        } else if(this.getMask()==16){
            if(this.getAdress().getAdressInt()[0]>127&&this.getAdress().getAdressInt()[0]<192 && this.getAdress().getAdressInt()[1]>0 && this.getAdress().getAdressInt()[1]<255){
                klasa = 'B';
            }
        } else if(this.getMask()==24){
            if(this.getAdress().getAdressInt()[0]>=192&&this.getAdress().getAdressInt()[0]<=224 && this.getAdress().getAdressInt()[1]>=0 && this.getAdress().getAdressInt()[1]<=255&&this.getAdress().getAdressInt()[2]>=1 &&this.getAdress().getAdressInt()[2]<=254)
            klasa = 'C';
        }
        return klasa;
    }
    public Adress getBroadcast(){
        boolean[][] broadcastBin = new Adress(this.getAdress()).getAdressBin();
        int k=0;
        for(int i=0;i<Global.getWidth();i++){
            for(int j=0;j<Global.getNum();j++){
                if(k>=this.getMask()){
                    broadcastBin[i][j] = true;
                }
                else {
                k++;
                }
            }
        }
        return new Adress(broadcastBin);
    }
    public boolean[][] getMaskBin(){
        boolean[][] returnTab = new boolean[Global.getWidth()][Global.getNum()];
        int i,j;
        int k=this.getMask();
        for(i=0 ;i<Global.getWidth();i++){
            for(j=0;j<Global.getNum();j++){
                if(k>0){
                    returnTab[i][j]=true;
                    k--;
                }
                else
                {
                    returnTab[i][j]=false;
                }
            }
        }
        return returnTab;
    }
    public int[] getMaskInt(){
        return Adress.binToInt(this.getMaskBin());
    }
    public int getHostRange(){
        return (int)Math.pow(2, (Global.getWidth()*Global.getNum())-this.mask)-2;
    }
    public Set getHosts(){
        return this.hosts;
    }

    //-------------------------------------------------------------------------------------------Overrides
    @Override
    public String toString(){
        String returnVale=new String("");
        //System.out.print("adres: ");
        returnVale =this.getName()+": "+ Adress.adrIntToString(this.getAdress().getAdressInt())+"/"+this.getMask();
        return returnVale;
    }

    //-------------------------------------------------------------------------------------------Static methods

    //-------------------------------------------------------------------------------------------private methods



    //-------------------------------------------------------------------------------------------public methods
    public Adress getFirstHost(){
        Adress returnVale = new Adress(this.getAdress());
        returnVale.changeVal(Global.getWidth()-1,Global.getNum()-1);
        return returnVale;
    }
    public Adress getLastHost(){
        Adress returnVale = new Adress(this.getBroadcast());
        returnVale.changeVal(Global.getWidth()-1,Global.getNum()-1);
        return returnVale;
    }
    public boolean addHost(Host ...hst){
        this.hosts.addAll(Arrays.asList(hst));
        return true;
    }
    public void show(){
        System.out.println(this.getAdress()+"/"+this.getMask()+":");
        for(Host hst: this.hosts){
            System.out.println("\t"+hst.getAdress());
        }
    }
    public String print(){
        String ret = "Dane Sieci "+this.getName()+": \n" +
                "Adres sieci: " +  this.getAdress().toString()+"\nKlasa sieci: "+this.getKlass();
        ret += "\n Maska dziesiętnie: " + Adress.adrIntToString(this.getMaskInt());
        ret += "\n Maska bnarnie: " + Adress.adrBinToString(this.getMaskBin());
        ret += "\n Broadcast dziesiętnie: " + Adress.adrIntToString(this.getBroadcast().getAdressInt());
        ret += "\n Broadcast bnarnie: " + Adress.adrBinToString(this.getBroadcast().getAdressBin());
        ret += "\n Pierwszy host dziesiętnie: " + Adress.adrIntToString(this.getFirstHost().getAdressInt());
        ret += "\n Pierwszy host bnarnie: " + Adress.adrBinToString(this.getFirstHost().getAdressBin());
        ret += "\n Ostatni host dziesiętnie: " + Adress.adrIntToString(this.getLastHost().getAdressInt());
        ret += "\n Ostatni host bnarnie: " + Adress.adrBinToString(this.getLastHost().getAdressBin());
        ret += "\n maksymalna liczba hostów dla podsieci: "+this.getHostRange();
        ret += "\n Lista hostów: ";
        for(int i=0; i<hosts.size();i++){
            ret +="\n\t"+ hosts.toArray()[i].toString();
        }
        return ret;
    }
}


 /*private static boolean[][] intToBinVH(int ...tab) {
        for(int i=0; i<tab.length;i++){
            if(tab[i]>255 || tab[i]<0){
                System.err.println("podano błędny adres");
            }
        }
        int i=0;
        boolean ret[][];
        ret= new boolean[Global.getWidth()][Global.getNum()];
        for(int j=i;j<tab.length&&j<Global.getWidth();j++)
        {
            int z=2;
            for(int l=1; l<Global.getNum();l++)
            {
                z=z*2;
            }
            int temp=tab[j]%z;
            for(int k=Global.getNum() ;k>0;k--)
            {
                if(temp%2==1)
                {
                    ret[j][k-1] = true;
                    temp--;
                }
                else
                {
                    ret[j][k-1] = false;
                }
                temp = temp/2;
            }
            i++;
        }
        for(int j=i;j<Global.getWidth();j++)
        {
            for(int k=Global.getNum();k>0;k--)
            {
                ret[j][k-1] = false;
            }
        }
        return ret;
    }
    private static int[] binToInt(boolean tab[][]){
        int[] returnTab = new int[tab.length];
        for(int i=0;i<Global.getWidth();i++){
            //System.out.println("---"+i+"---");
            for(int j=0; j<Global.getNum();j++){
                if(tab[i][j]){
                    //      System.out.println(j);
                    returnTab[i]+=Math.pow(2, Global.getNum()-j-1);
                }
            }
        }
        return returnTab;
    }
    private static String adrIntToString(int tab[]){
        String returnVale = "";
        for(int i=0; i<tab.length;i++){
            returnVale=returnVale+tab[i];
            if(i<tab.length-1){
                returnVale = returnVale+".";
            }
        }
        return returnVale;
    }*/