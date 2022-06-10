package org.example.commands;

import org.example.database.ConnectionProvider;
import org.example.datacollectors.DataCollector;
import org.example.infos.InfoStatus;
import org.example.utils.DataSender;
import org.example.validators.SimpleValidator;
import org.example.validators.Validator;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class RemoteCommandListener implements CommandListener{

    boolean listening = false;

    private final Socket socket;

    private final DataCollector dataCollector;

    private final ConnectionProvider connectionProvider;

    private final InfoStatus infoStatus;

    private final Validator validator = SimpleValidator.getINSTANCE();

    private boolean isLoggedIn = false;




    public RemoteCommandListener(Socket socket, DataCollector dataCollector, ConnectionProvider connectionProvider, InfoStatus infoStatus) {
        this.socket = socket;
        this.dataCollector = dataCollector;
        this.connectionProvider = connectionProvider;
        this.infoStatus = infoStatus;
    }


    @Override
    public boolean isListening() {
        return listening;
    }

    @Override
    public void startListening() {
        String temperatureDcs = "Temperature collecting data source: '" + dataCollector.getTemperatureSourcePath() + "' COLLECTING: |" + infoStatus.collectingStatus() + "|";
        String humidityDcs = "Humidity collecting  data source: '" + dataCollector.getHumiditySourcePath() + "' COLLECTING: |" + infoStatus.collectingStatus() + "|";
        String freqInfo = "Collecting frequency: " + dataCollector.getCollectingFrequency() + "min.";
        String dbSource = "Database source: '" + connectionProvider.getUrl() + "' STATUS: |" + infoStatus.dbStatus() + "|";
        String helpInfo = "Type '\\help' for list available commands and more information.";

        try{
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream output = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            DataSender dataSender = new DataSender(output);
            while (listening && !isLoggedIn){
                String login = input.readLine();
                String userName = "user";
                if(login.equals(userName)){
                    dataSender.sendBoolean(true);
                    String pass= input.readLine();
                    String password = "admin12";
                    if(pass.equals(password)){
                        dataSender.sendBoolean(true);
                        isLoggedIn = true;
                        dataSender.sendText(temperatureDcs,humidityDcs, freqInfo,dbSource, helpInfo);

                    }
                    else{
                        dataSender.sendBoolean(false);
                    }
                }
                else{
                    dataSender.sendBoolean(false);
                }

            }

            while (listening && isLoggedIn){
                String command = input.readLine();

                if(command != null) {

                    if (command.equals(Command.INFO.getCommand())) {

                        dataSender.sendText(temperatureDcs,humidityDcs,freqInfo,dbSource);

                    }
                    else if(command.equals("\\logout")){
                        dataSender.sendText("Logout successful.");
                        isLoggedIn = false;

                    }
                    else if(command.equals(Command.HELP.getCommand())){
                        dataSender.sendText(getCommands());
                    }

                    else if(CommandUtils.isSetDBHostCommandCalled(command)){
                        String host = getSplitValue(command);
                        connectionProvider.setHost(host);
                        String response = "Host updated to: "+host;
                        dataSender.sendText(response);

                    }
                    else if(CommandUtils.isSetDBPortCommandCalled(command)){
                        String port = getSplitValue(command);
                        String response;
                        if(validator.isPortValid(port)){
                            connectionProvider.setPort(port);
                            response = "Port updated to: " + port;
                        }
                        else {
                            response = "Port not valid";
                        }
                        dataSender.sendText(response);

                    }
                    else if(CommandUtils.isSetDBNameCommandCalled(command)){
                        String name = getSplitValue(command);
                        connectionProvider.setDataBaseName(name);
                        String response = "Name updated to: " + name;
                        dataSender.sendText(response);

                    }
                    else if(CommandUtils.isSetCDSTemperatureCommandCalled(command)){
                        String path = getSplitValue(command);
                        dataCollector.setTemperatureSourcePath(path);
                        String response = "Temperature collecting datasource updated to " + path;
                        dataSender.sendText(response);

                    }
                    else if(CommandUtils.isSetCDSHumidityCommandCalled(command)){
                        String path = getSplitValue(command);
                        dataCollector.setHumiditySourcePath(path);
                        String response = "Humidity collecting datasource updated to " + path;
                        dataSender.sendText(response);

                    }
                    else if(CommandUtils.isSetCDSFrequencyCommandCalled(command)){
                        String response;
                        try {
                            byte frequency = Byte.parseByte(getSplitValue(command));
                            dataCollector.setCollectingFrequency(frequency);
                            response = "Collecting frequency updated to: " + frequency;
                        } catch (IllegalArgumentException e){
                            response = e.getMessage();
                        }
                        dataSender.sendText(response);

                    }
                    else if(CommandUtils.isSetCDSCollectingCommandCalled(command)){
                        String choice = getSplitValue(command);
                        if(choice.equals("on")){
                            if(dataCollector.isCollecting()){
                                dataSender.sendText("Data collector is already collecting.");
                            }
                            else{
                                dataCollector.collectingOn();
                                dataSender.sendText("Collecting turned on.");
                            }
                        }
                        else if(choice.equals("off")){
                            if(!dataCollector.isCollecting()){
                                dataSender.sendText("Data collector is already turned off.");
                            }
                            else{
                                dataCollector.collectingOff();
                                dataSender.sendText("Collecting turned off.");
                            }
                        }
                        else{
                            dataSender.sendText("Wrong parameter. Should be 'on' or 'off'.");
                        }
                    }
                    


                    else {
                        String errorMsg = "Command '" + command + "' is wrong.";
                        dataSender.sendText(errorMsg);
                    }

                }
            }

        }catch (IOException e){
            System.out.println("Server exception " + e.getMessage());
        }finally {
            try {
                socket.close();
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
        }

    }
    private String getSplitValue(String target){
        String[] split = target.split(" ");
        return split[3];
    }

    private String[] getCommands(){
        List<String> result = new ArrayList<>();
        for(Command com: Command.values()){
            result.add(String.format("'%s' - %s%n", com.getCommand(), com.getDescription()));
        }
        result.add("'\\logout' - Logout and close application.");

        return result.toArray(new String[0]);
    }


}
