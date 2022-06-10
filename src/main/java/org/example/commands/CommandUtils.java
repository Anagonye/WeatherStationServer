package org.example.commands;

public class CommandUtils {

    private CommandUtils(){}


    public static boolean isSetDBPortCommandCalled(String command) throws NullPointerException{
        return isCommandCalled("\\set","-db","-port", command);
    }
    public static boolean isSetDBHostCommandCalled(String command) throws NullPointerException{
        return isCommandCalled("\\set","-db","-host", command);
    }

    public static boolean isSetDBNameCommandCalled(String command) throws NullPointerException{
        return isCommandCalled("\\set","-db","-name", command);
    }

    public static boolean isSetCDSTemperatureCommandCalled(String command) throws NullPointerException{
        return isCommandCalled("\\set","-cds","-temperature", command);
    }
    public static boolean isSetCDSHumidityCommandCalled(String command) throws NullPointerException{
        return isCommandCalled("\\set","-cds","-humidity", command);
    }

    public static boolean isSetCDSFrequencyCommandCalled(String command) throws NullPointerException {
        return isCommandCalled("\\set","-cds","-freq", command);
    }

    public static boolean isSetCDSCollectingCommandCalled(String command) throws NullPointerException{
       return isCommandCalled("\\set","-cds","-col", command);
    }


    public static boolean isCommandCalled(String argument1, String argument2, String argument3, String command) throws NullPointerException{
        if (command == null){
            throw new NullPointerException();
        }
        if(!command.contains(" "))
            return false;
        String[] split = command.split(" ");
        if(split.length != 4 )
            return false;
        if(!split[0].equals(argument1))
            return false;
        if(!split[1].equals(argument2))
            return false;
        return split[2].equals(argument3);
    }
}
