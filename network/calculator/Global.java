package network.calculator;

public class Global {
    private static int width=4;
    private static int num=8;
    private static boolean consoleMode;
    static int getWidth()
    {
        return width;
    }
    static int getNum()
    {
        return num;
    }
    public static boolean isConsoleMode() {
        return consoleMode;
    }
    public static void setConsoleMode(boolean consoleMode) {
        Global.consoleMode = consoleMode;
    }
    public static void setWidth(int width) {
        Global.width = width;
    }
    public static void setNum(int num) {
        Global.num = num;
    }
}
