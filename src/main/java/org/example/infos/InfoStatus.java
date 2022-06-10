package org.example.infos;

import org.example.colors.Color;
import org.example.colors.ConsoleColor;
import org.example.database.ConnectionProvider;
import org.example.datacollectors.DataCollector;


public class InfoStatus {

    private final DataCollector dataCollector;
    private final ConnectionProvider connectionProvider;

    public InfoStatus(DataCollector dataCollector, ConnectionProvider connectionProvider) {
        this.dataCollector = dataCollector;
        this.connectionProvider = connectionProvider;
    }

    public  String collectingStatus(){
        if(dataCollector.isCollecting())
            return ConsoleColor.colorIt("ON", Color.GREEN);
        return ConsoleColor.colorIt("OFF", Color.RED);
    }

    public  String dbStatus() {
        if(connectionProvider.isConnected())
            return ConsoleColor.colorIt("Connected", Color.GREEN);
        return ConsoleColor.colorIt("Disconnected", Color.RED);
    }
}
