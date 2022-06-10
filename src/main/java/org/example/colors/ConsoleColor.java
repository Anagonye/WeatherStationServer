package org.example.colors;

public class ConsoleColor {

    public static String colorIt(String target, Color color){
        return color.getANSI_COLOR() + target + Color.RESET.getANSI_COLOR();
    }

}
