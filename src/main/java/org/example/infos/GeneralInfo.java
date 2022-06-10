package org.example.infos;

import org.example.database.ConnectionProvider;
import org.example.datacollectors.DataCollector;




public final class GeneralInfo {
    private final String VERSION = "0.1";
    private final DataCollector dataCollector;
    private final ConnectionProvider connectionProvider;
    private final InfoStatus infoStatus;

    public GeneralInfo(DataCollector dataCollector, ConnectionProvider connectionProvider, InfoStatus infoStatus) {
        this.dataCollector = dataCollector;
        this.connectionProvider = connectionProvider;
        this.infoStatus = infoStatus;
    }

    public void printInfo(){
        System.out.println("Server VERSION: " + VERSION);
        System.out.println("Temperature collecting data source: '" + dataCollector.getTemperatureSourcePath() + "' COLLECTING: |" + infoStatus.collectingStatus() + "|");
        System.out.println("Humidity collecting  data source: '" + dataCollector.getHumiditySourcePath() + "' COLLECTING: |" + infoStatus.collectingStatus() + "|");
        System.out.println("Collecting frequency: " + dataCollector.getCollectingFrequency() + "min.");
        System.out.println("Database source: '" + connectionProvider.getUrl() + "' STATUS: |" + infoStatus.dbStatus() + "|");


    }




}
