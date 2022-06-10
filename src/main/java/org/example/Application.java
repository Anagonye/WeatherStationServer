package org.example;


import org.example.commands.CommandListener;
import org.example.commands.LocalCommandListener;
import org.example.config.ConnectionProviderConfig;
import org.example.config.DataCollectorConfig;
import org.example.database.ConnectionProvider;
import org.example.errors.ErrorLogger;
import org.example.infos.InfoStatus;
import org.example.json.JsonReader;
import org.example.measurements.*;
import org.example.datacollectors.DataCollector;
import org.example.datacollectors.DataCollectorThread;
import org.example.datacollectors.ESPDataCollector;
import org.example.config.ConfigManager;
import org.example.infos.GeneralInfo;
import org.example.remote.RemoteManager;
import org.example.utils.ThreadManager;

import java.util.Scanner;

public class Application {

    public void start(){

        //Setting up config files:
        var configManager = new ConfigManager();
        configManager.createConfigFile();

        ConnectionProviderConfig connectionProviderConfig = configManager.readConnectionProviderConfig().orElseGet(configManager::getConnectionProviderDefaultConfig);

        DataCollectorConfig dataCollectorConfig = configManager.readCollectorConfig().orElseGet(configManager::getCollectorDefaultConfig);


        //Setting up Database connection:
        var connectionProvider = new ConnectionProvider(connectionProviderConfig);
        var errorLogger = new ErrorLogger(connectionProvider);
        MeasurementDao<Temperature> tempDao = new TemperatureDao(connectionProvider, errorLogger);
        MeasurementDao<Humidity> humDao = new HumidityDao(connectionProvider, errorLogger);



        //Setting up Data collector:
        var jsonReader = new JsonReader();
        DataCollector dataCollector = new ESPDataCollector(dataCollectorConfig, jsonReader, errorLogger );

        //Setting up infos:
        var infoStatus = new InfoStatus(dataCollector,connectionProvider);
        var generalInfo = new GeneralInfo(dataCollector, connectionProvider,infoStatus );

        //Setting up Remote manager:
        var remoteManager = new RemoteManager(dataCollector, connectionProvider,infoStatus );


        //Setting up threads:
        DataCollectorThread dataCollectorThread = new DataCollectorThread(dataCollector,tempDao,humDao);
        ThreadManager threadManager = new ThreadManager(dataCollectorThread, remoteManager);

        //Setting up Scanner:
        Scanner scanner = new Scanner(System.in);

        //Setting up Command listener:
        CommandListener commandListener = new LocalCommandListener(
                scanner,
                threadManager,
                dataCollector,
                connectionProvider,
                configManager,
                generalInfo);

        dataCollectorThread.start();
        remoteManager.start();
        System.out.println("Weather Station Server is running.");
        generalInfo.printInfo();
        System.out.println("Type '\\help' for list available commands and more information.");



        do{

        commandListener.startListening();

        }while (commandListener.isListening());
    }







}
