package org.example.config;

import java.io.*;
import java.util.Optional;


public class ConfigManager {
    private final String directoryName = "configuration";
    private final String collectorFileName = "collector-config";
    private final String providerFileName = "connection-provider-config";
    private final File directory = new File(directoryName);
    private final File collectorConfigFile = new File(directoryName +"/"+collectorFileName);
    private final File providerConfigFile = new File(directoryName+"/"+providerFileName);
    private boolean isDirectoryExist = directory.exists();
    private boolean isCollectorFileExist = collectorConfigFile.exists();
    private boolean isProviderFileExist = providerConfigFile.exists();



    public void createConfigFile(){
        createDir();
        createEmptyFiles();
    }


    public Optional<DataCollectorConfig> readCollectorConfig(){
        return readConfig(collectorConfigFile);
    }

    public void writeCollectorConfig(DataCollectorConfig config){
        writeConfig(collectorConfigFile, config);
    }

    public Optional<ConnectionProviderConfig> readConnectionProviderConfig(){
        return readConfig(providerConfigFile);
    }

    public void writeConnectionProviderConfig(ConnectionProviderConfig config){
        writeConfig(providerConfigFile, config);
    }




    private <T> void writeConfig(File configFile, T t){
        try(
                var fileOS = new FileOutputStream(configFile);
                var objectOS = new ObjectOutputStream(fileOS)
                )
        {
            objectOS.writeObject(t);
            System.out.println("Configuration has been saved.");

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }


    private <T> Optional<T> readConfig(File configFile){
        try(
                var fileIS = new FileInputStream(configFile);
                var objectIS = new ObjectInputStream(fileIS)
                )
        {
            System.out.println("Configuration has been loaded.");
            return Optional.of((T)objectIS.readObject());

        }catch (IOException | ClassNotFoundException e){
            return Optional.empty();
        }
    }

    public DataCollectorConfig getCollectorDefaultConfig(){
        String defaultDCTemperaturePath = "http://172.25.100.240/temperature";
        String defaultDCHumidityPath = "http://172.25.100.240/humidity";
        byte frequency = 10;

        System.out.println("Data collector default configuration has been loaded.");
        return new DataCollectorConfig(
                defaultDCTemperaturePath,
                defaultDCHumidityPath,
                frequency,
                true
                );

    }

    public ConnectionProviderConfig getConnectionProviderDefaultConfig(){
        String host = "localhost";
        String port = "5432";
        String dataBaseName = "test3";
        String user = "postgres";
        String password = "toor";


        System.out.println("Connection provider default configuration has been loaded.");
        return new ConnectionProviderConfig(
                host,
                port,
                dataBaseName,
                user,
                password


        );
    }

    private void createEmptyFiles(){
        if(isDirectoryExist){
            if(!isCollectorFileExist){
                try {
                    isCollectorFileExist = collectorConfigFile.createNewFile();
                }catch (IOException e){
                    System.out.println(e.getMessage());
                }
            }
            if(!isProviderFileExist){
                try {
                    isProviderFileExist = providerConfigFile.createNewFile();
                }catch (IOException e){
                    System.out.println(e.getMessage());
                }
            }
        }
    }


    private void createDir(){
        if(!isDirectoryExist){
            isDirectoryExist = directory.mkdir();
            System.out.println("Dir created.");
        }
        else{
            System.out.println("Dir already exist.");
        }
    }





}
