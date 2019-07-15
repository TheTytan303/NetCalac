package network.calculator;

import java.lang.*;



public class Adress implements Comparable<Adress>, Cloneable{

    private boolean[][] adressBin;

    //-------------------------------------------------------------------------------------------Constructor
    public Adress(int tab[]) {
        this(intToBinVH(tab));
    }
    public Adress(boolean tab[][]) {
        adressBin = tab;
    }
    public Adress(Adress adr){
        this.adressBin = new boolean[Global.getWidth()][Global.getNum()];
        for(int i=0; i<Global.getWidth(); i++){
            for(int j=0; j<Global.getNum();j++){
                this.adressBin[i][j] = adr.getAdressBin()[i][j];
            }
        }
    }

    //-------------------------------------------------------------------------------------------Geter
    public boolean getVal(int a, int b)
    {
        return adressBin[a][b];
    }
    public boolean[][] getAdressBin()
    {
        return adressBin;
    }
    public int[] getAdressInt()
    {
        return Adress.binToInt(this.adressBin);
    }

    //-------------------------------------------------------------------------------------------Setter
    public void setAdressBin(boolean[][] adressBin) {
        this.adressBin = adressBin;
    }
    public void changeVal(int a, int b) {
        boolean c= !adressBin[a][b];
        adressBin[a][b]=c;
    }
    //-------------------------------------------------------------------------------------------Overrides
    @Override
    public int compareTo(Adress adr){
        int[] adressInt1 = this.getAdressInt();
        int[] adressInt2 = adr.getAdressInt();
        for(int i=0;i<Global.getWidth();i++){
            if(adressInt1[i]!=adressInt2[i]){
                return Integer.compare(adressInt1[i], adressInt2[i]);
                //return new Integer(adressInt1[i]).compareTo(new Integer(adressInt2[i]));
            }
        }
        return 0;
    }
    @Override
    public String toString(){
        String returnVale=new String("");
        //System.out.print("adres: ");
        returnVale = Adress.adrIntToString(this.getAdressInt());
        return returnVale;
    }

    //-------------------------------------------------------------------------------------------Static methods
    public static boolean[][] intToBinVH(int ...tab) {
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
    public static int[] binToInt(boolean tab[][]){
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
    public static String adrIntToString(int tab[]){
        String returnVale = "";
        for(int i=0; i<tab.length;i++){
            returnVale=returnVale+tab[i];
            if(i<tab.length-1){
                returnVale = returnVale+".";
            }
        }
        return returnVale;
    }
    public static String adrBinToString(boolean tab[][]){
        String returnVale = "";
        for(int i=0;i<Global.getWidth();i++)
        {
            for(int j=0; j<Global.getNum();j++)
            {
                if(tab[i][j])
                {
                    returnVale+="1";
                }
                else
                {
                    returnVale+="0";
                }
                //System.out.println(this.getVal(i, j));
            }
            if(i!=Global.getWidth()-1)
            {
                returnVale+=".";
            }
        }
        return returnVale;
    }
    public static Adress StringToAdress(String value){
        int[] argTab = new int[Global.getNum()];
        String[] tabStr=value.split("\\.");
        for(int i=0;i<tabStr.length;i++){
            argTab[i] = Integer.valueOf(tabStr[i]).intValue();
        }
        Adress returnVale = new Adress(argTab);
        return returnVale;
    }
    public static boolean isStringAddress(String ...adr){
        for(int i=0; i<adr.length;i++){
            String tmp = adr[i];
            String[] tabStr2=tmp.split("\\.");
            if(tabStr2.length!=Global.getWidth()){
                return false;
            }


            for(int j=0; j<tabStr2.length;j++) {
                try{
                    if(Integer.valueOf(tabStr2[j])<0 ||Integer.valueOf(tabStr2[j]) >255){
                        return false;
                    }
                }catch(Exception e){
                    return false;
                }
            }
        }
        return true;
    }


    //-------------------------------------------------------------------------------------------public methods
    public void showBin() {
        System.out.println("adress: ");
        System.out.println(Adress.adrBinToString(this.getAdressBin()));
    }
    public void showInt(){
        int [] tab = Adress.binToInt(this.adressBin);
        System.out.print("adres: ");
        for(int i=0; i<tab.length;i++){
            System.out.print(tab[i]+".");
        }
    }
    public String print(){
        System.out.println("");
        return "";
    }

}




/*public class Adress {
    private int rawAdress[];
    private int mask;
    Adress(int Tab[], int mask){
        rawAdress = new int [4];
        if(Tab.length<4)
        {
            System.out.println("Adres niekompletny - uzupełniam zerami...");
        }
        int i;
        for(i=0;i<Tab.length && i<rawAdress.length; i++)
        {
            rawAdress[i] = Tab[i]%256;
        }
        for(int j=i;j<rawAdress.length; j++)
        {
            rawAdress[j] = 0;
        }
        System.out.println("utworzono nowy Adres: ");
        for(i=0;i<rawAdress.length; i++)
        {
            System.out.print(Integer.toBinaryString(rawAdress[i])+".");
        }
        System.out.println("\n o masce: ");
        for(i=0;i<mask; i++)
        {
            System.out.print("1");
            if(i%8==0&&i!=0)
            {
                System.out.print(".");
            }
        }
        for(int j=i;j<mask; j++)
        {
            System.out.print("0");
            if(j%8==0&&j!=0)
            {
                System.out.print(".");
            }
        }
    }
}
/*
    private boolean Bin[][] = new boolean[4][8];
    private int mask;

    Adress(int tab[], int mask)
    {
        this(intToBin(tab), mask);
    }
    Adress(boolean tab[][], int mask2)
    {
        Bin=tab;
        mask = mask2;
    }
    Adress(Adress N)
    {


    }
    private static boolean[][] intToBin(int tab[])
    {

    }*/