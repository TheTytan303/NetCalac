package network.calculator;

public class InvalidInputExpection extends Exception {
    private String msg;
    public InvalidInputExpection(String e){
        msg=e;
    }
    public String getMsg() {
        return msg;
    }
}