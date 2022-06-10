package org.example.commands;


import org.example.config.ConfigManager;
import org.example.database.ConnectionProvider;
import org.example.datacollectors.DataCollector;
import org.example.infos.GeneralInfo;
import org.example.utils.ThreadManager;
import org.example.validators.SimpleValidator;
import org.example.validators.Validator;

import java.util.Scanner;

public class LocalCommandListener implements CommandListener {
    private boolean listening = false;
    private final Scanner scanner;

    private final ThreadManager threadManager;
    private final Validator validator = SimpleValidator.getINSTANCE();
    private final DataCollector dataCollector;
    private final ConnectionProvider connectionProvider;
    private final ConfigManager configManager;
    private final GeneralInfo generalInfo;

    public LocalCommandListener( Scanner scanner,
                                 ThreadManager threadManager,
                                 DataCollector dataCollector,
                                 ConnectionProvider connectionProvider,
                                 ConfigManager configManager,
                                 GeneralInfo generalInfo){
        this.scanner = scanner;
        this.threadManager = threadManager;
        this.dataCollector = dataCollector;
        this.connectionProvider = connectionProvider;
        this.configManager = configManager;
        this.generalInfo = generalInfo;
    }

    public void startListening(){
        listening = true;
        String command = scanner.nextLine().strip();
        if(command.equals(Command.INFO.getCommand())){
            generalInfo.printInfo();
        }
        else if(command.equals("\\exit")){
            System.out.println("Are you sure you want close program?[y/n]");
            String choice;
            do {
                choice = scanner.next();
                if(choice.equals("y")){
                    threadManager.stopDataCollectingThread();
                    threadManager.stopRemoteManager();
                    configManager.writeConnectionProviderConfig(
                            connectionProvider.getCurrentConfig()
                    );
                    configManager.writeCollectorConfig(
                            dataCollector.getCurrentConfig()
                    );

                    listening = false;
                    break;
                }
                else if(choice.equals("n")){
                    break;
                }
                else{
                    System.out.println("Please type 'y' or 'n'");
                }


           }while (true);


        }
        else if(command.equals(Command.HELP.getCommand())){
            System.out.println("Available commands: ");
            for(Command com:Command.values()){
                System.out.printf("'%s' - %s%n", com.getCommand(), com.getDescription());
            }
            System.out.println("'\\exit' - Close the application." );
        }


        else if(CommandUtils.isSetDBPortCommandCalled(command)){
            String port = getSplitValue(command);
            if(validator.isPortValid(port)){
                connectionProvider.setPort(port);
                System.out.println("Port number updated successful.");
            }
            else{
                System.out.println("Port: " + port + " not valid.");
            }
        }
        else if(CommandUtils.isSetDBHostCommandCalled(command)){
            String host = getSplitValue(command);
            try {
                connectionProvider.setHost(host);
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
        else if(CommandUtils.isSetDBNameCommandCalled(command)){
            String name = getSplitValue(command);
            try {
                connectionProvider.setDataBaseName(name);
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
        else if(CommandUtils.isSetCDSTemperatureCommandCalled(command)){
            String dataSource = getSplitValue(command);
            if(validator.isDataSourceValid(dataSource)){
                dataCollector.setTemperatureSourcePath(dataSource);
            }
        }
        else if(CommandUtils.isSetCDSHumidityCommandCalled(command)){
            String dataSource = getSplitValue(command);
            if(validator.isDataSourceValid(dataSource)){
                dataCollector.setHumiditySourcePath(dataSource);
            }
        }
        else if(CommandUtils.isSetCDSFrequencyCommandCalled(command)){
            try {
                byte frequency = Byte.parseByte(getSplitValue(command));
                dataCollector.setCollectingFrequency(frequency);
            } catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }

        else if(CommandUtils.isSetCDSCollectingCommandCalled(command)){
            String choice = getSplitValue(command);
            if(choice.equals("on")){
                if(dataCollector.isCollecting()){
                    System.out.println("Data collector is already collecting.");
                }
                else{
                    dataCollector.collectingOn();
                    System.out.println("Collecting turned on.");
                }
            }
            else if(choice.equals("off")){
                if(!dataCollector.isCollecting()){
                    System.out.println("Data collector is already turned off.");
                }
                else{
                    dataCollector.collectingOff();
                    System.out.println("Collecting turned off.");
                }
            }
            else{
                System.out.println("Wrong parameter. Should be 'on' or 'off'.");
            }
        }
        else{
            System.out.println("Command: '" + command + "' is wrong.");
        }


    }

    public boolean isListening() {
        return listening;
    }

    private String getSplitValue(String target){
        String[] split = target.split(" ");
        return split[3];
    }




}
